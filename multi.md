# Multi-thred

## Concurrency

in order to do the interleaving, the system has to perform a *context switch* every time it changes
from one task to another, and this takes time.

In order to perform a *context switch*,
the OS has to save the *CPU state* and *instruction pointer* for the currently running
task, work out *which task to switch to*, and reload the CPU state for the task being
switched to. The CPU will then potentially have to *load the memory for the*
*instructions* and *data for the new task into the cache*, which can prevent the CPU
from executing any instructions, causing further delay.

Two basic approachesto concurrency. The first approach is to have *multiple*
*single-threaded processes*, which is similar to having each developer in their
own office, and the second approach is to have *multiple threads in a single*
*process*, which is like having two developers in the same office.

### Multiple Process

The first way to make use of concurrency within an application is to divide the
application into multiple, separate, single-threaded processes that are run at
the same time, much as you can run your web browser and word processor at the
same time.

These separate processes can then pass messages to each other through all the
normal inter-process communication channels (signals, sockets, files, pipes,
and so on) The added protection operating systems typically provide
between processes and the higher-level communication mechanisms mean that it
can be easier to write safe concurrent code with processes rather than threads.

(Erlang and elixir are examples of languages that use processes.)

Using separate processes for concurrency also has an additional advantage--you
can run the separate processes on distinct machines connected over a network.

> So, in the end, the question is: what are you going to build with it? Machine
learning? Scientific computations? Recommendation systems? Take Scala :) Web
applications? Game servers? Anything that serves user requests? Take Elixir.
[Choosing between Akka and Elixir](https://www.reddit.com/r/elixir/comments/fbhnxq/choosing_between_akka_and_elixir/)

### Multiple Thread

All threads in a process share the same address space, and most of the data
can be accessed directly from all threads—global variables remain global, and
pointers or references to objects or data can be passed around among threads.

But the flexibility of shared memory also comes with a price: if data is
accessed by multiple threads, the application programmer must ensure that the
view of data seen by each thread is consistent whenever it’s accessed.

### Why?

There are two main reasons to use concurrency in an application: separation of
concerns and performance.

Fundamentally, the only reason not to use concurrency is when the benefit isn’t
worth the cost. Code using concurrency is harder to understand in many cases, so
there’s a direct intellectual cost to writing and maintaining multithreaded code, and
the additional complexity can also lead to more bugs.

Unless the potential performance gain is large enough or the separation of
concerns is clear enough to justify the additional development time required to
get it right and the additional costs associated with maintaining multithreaded
code, don’t use concurrency.

### History

Although the precise details of the class libraries vary considerably,
particularly in the area of launching new threads, the overall shape of the
classes has a lot in common.One particularly important design that’s common to
many C++ class libraries, and that provides considerable benefit to the
programmer, is the use of the Resource Acquisition Is Initialization (RAII)
idiom with locks to ensure that mutexes are unlocked when the relevant scope is
exited.

This cost is the `abstraction penalty`.

## Basic

```cpp
std::thread my_thread([]() {
    // do something
});
```

Once you’ve started your thread, you need to explicitly decide whether to wait for it
to finish (by joining with it) or leave it to run on its own (by detaching it).

Note that you only have to make this decision before the `std::thread` object is destroyed.

the thread itself may well have finished long before you join with it or detach
it, and if you detach it, then if the thread is still running, it will continue
to do so, and may continue running long after the `std::thread` object is
destroyed; it will only stop running when it finally *returns* from the thread
function.

### JOIN

One common way to handle this scenario (dangling pointers) is to make the thread function self-contained
and copy the data into the thread rather than sharing the data. Alternatively,
you can ensure that the thread has completed execution before the function exits
by *joining* with the thread.

`join()` is a simple and brute-force technique—either you wait for a thread to fin-
ish or you don’t.
If you need more fine-grained control over waiting for a thread, such
as to check whether a thread is finished, or to wait only a certain period of time, then
you have to use alternative mechanisms such as *condition variables* and *futures*.

### DETACH

Calling `detach()` on a `std::thread` object leaves the thread to run in the
background, with no direct means of communicating with it.
It’s no longer possible to wait for that thread to complete;

Detached threads are often called *daemon threads* after the UNIX concept of a
*daemon process* that runs in the background without any explicit user
interface.

## Shared Memory

It’s the same with threads. If you’re sharing data between threads, you need to
have rules for which thread can access which bit of data when, and how any updates
are communicated to the other threads that care about that data.

> If all shared data is read-only, there’s no problem, because the data read by
one thread is unaffected by whether or not another thread is reading the same
data.

But if data is shared between threads, and one or more threads start modify-
ing the data, there’s a lot of potential for trouble.

In concurrency, a race condition is anything where the outcome depends on the
relative ordering of execution of operations on two or more threads; the threads race
to perform their respective operations.

There are several ways to deal with problematic race conditions. The simplest option
is to wrap your data structure with a *protection mechanism* to ensure that only the
thread performing a modification can see the intermediate states where the invariants
are broken.

Another option is to modify the design of your data structure and its invariants so
that modifications are done as a series of indivisible changes, each of which preserves
the invariants. This is generally referred to as *lock-free programming* and is difficult to get
right.

**mutex** (*mut*ual *ex*clusion)

>Don’t pass *pointers and references* to protected data outside the scope of the lock, whether by
returning them from a function, storing them in externally visible memory, or passing them as
arguments to user-supplied functions.

## Synchronization

- <https://putridparrot.com/blog/threads-promises-futures-async-c/>
- <https://stackoverflow.com/questions/17729924/when-to-use-promise-over-async-or-packaged-task>
- <http://jakascorner.com/blog/2016/03/promise-difference.html>
- <https://nikhilm.github.io/uvbook/basics.html>
- <https://stackoverflow.com/questions/62105010/i-have-a-question-about-c-boost-asio-and-std-async>
- <https://think-async.com/Asio/AsioAndBoostAsio.html>
- <https://stackoverflow.com/questions/27435284/multiprocessing-vs-multithreading-vs-asyncio-in-python-3>

Sometimes you don’t just need to protect the data, you also need to synchronize
actions on separate threads.  One thread might need to wait for another thread
to complete a task before the first thread can complete its own, for example.

>Technical Specification (TS)

The third and preferred option is to use the facilities from the C++ Standard
Library to wait for the event itself. The most basic mechanism for waiting for
an event to be triggered by another thread (such as the presence of additional
work in the pipeline mentioned previously) is the *condition variable*.

### Future

The C++ Standard Library models this sort of one-off event with something called
a future. A future may have data associated with it, or it may not. Once an
event has happened (and the future has become ready), the future can’t be
reset.

### Promise

What about those tasks that can’t be expressed as a simple function call or those
tasks where the result may come from more than one place?

`std::promise<T>` provides a means of setting a value (of type `T`) that can later be
read through an associated `std::future<T>` object. A `std::promise/std::future`
pair would provide one possible mechanism for this facility; the waiting thread could
block on the future, while the thread providing the data could use the promise half of
the pairing to set the associated value and make the future ready.

### Functional

If there are no modifications to shared data, there can be no race conditions
and thus no need to protect shared data with mutexes either.

### What about CSP

The idea of CSP is simple: if there’s no shared data, each thread can be
reasoned about entirely independently, purely on the basis of how it behaves in
response to the messages that it received.

Each thread is therefore effectively a state machine: when it receives a
message, it updates its state in some manner and maybe sends one or more
messages to other threads, with the processing performed depending on the
initial state.

- <https://stackoverflow.com/questions/19576505/boost-is-there-an-interprocessmessage-queue-like-mechanism-for-thread-only-co>
- <https://ntnuopen.ntnu.no/ntnu-xmlui/handle/11250/2453094>
- <https://telegra.ph/%E8%BD%AC%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8BActors%E6%A8%A1%E5%9E%8B%E5%92%8CCSP%E6%A8%A1%E5%9E%8B---C%E7%88%B1%E5%A5%BD%E8%80%85%E5%8D%9A%E5%AE%A2-04-04>
- <https://play.kotlinlang.org/hands-on/Introduction%20to%20Coroutines%20and%20Channels/08_Channels>
- <https://stackoverflow.com/questions/68742051/communicating-between-coroutines-in-asio>

#### LibGo

- <https://github.com/yyzybb537/libgonet>
- <https://github.com/yyzybb537/libgo>
- <https://gitee.com/yyzybb537/libgo>
- <https://www.zhihu.com/question/51817323>

### Coroutine

- <https://github.com/yyzybb537/libgo/wiki/Why-cpp-coroutine%3F-Why-libgo%3F>
- <https://www.zhihu.com/question/50185085>
- <https://developer.android.com/kotlin/coroutines?hl=zh-cn>
- <https://www.zhihu.com/question/20511233>
- <https://www.zhihu.com/question/32218874>
- <https://zhuanlan.zhihu.com/p/94909455>
- <https://netcan.github.io/2021/12/04/C-20%E5%8D%8F%E7%A8%8B%E7%89%B9%E6%80%A7%E6%80%A7%E8%83%BD%E6%B5%8B%E8%AF%95/>
- <https://medium.com/ing-blog/how-does-non-blocking-io-work-under-the-hood-6299d2953c74>
- <https://stackoverflow.com/questions/71621871/in-non-blocking-io-what-is-exactly-does-the-io>
- <https://stackoverflow.com/questions/65804753/threads-on-bare-metal-embedded-devices>
- [Coroutine in C Language](http://www.vishalchovatiya.com/coroutine-in-c-language/)
- [Coroutines in C](https://www.chiark.greenend.org.uk/~sgtatham/coroutines.html)
- [The Art of Computer Programming](https://en.wikipedia.org/wiki/The_Art_of_Computer_Programming)

[Coroutine and threads (Wikipedia)](https://en.wikipedia.org/wiki/Coroutine#Threads) and [yields](https://en.wikipedia.org/wiki/Yield_(multithreading))

> Coroutines are very similar to threads. However, coroutines are cooperatively
multitasked, whereas threads are typically preemptively multitasked. Coroutines
provide concurrency but not parallelism. The advantages of coroutines over
threads are that they may be used in a hard-realtime context (switching between
coroutines need not involve any system calls or any blocking calls whatsoever),
there is no need for synchronization primitives such as mutexes, semaphores,
etc. in order to guard critical sections, and there is no need for support from
the operating system.

> 在高IO密集型的程序下很好。但是高CPU密集型的程序下没啥好处。

Indeed, you are running in single thread. 
But here again, we are talking about single thread.

> 也就是说，协程并不是“线程”，理由是：它并不会参与 CPU 时间调度，并没有均衡分配到时间。

> 协程本来是个很古老的东西，很多年没多少人太在意过它，但针对异步回调反人类这件事，协程才又被人从故纸堆里翻出来了，因为它刚好可以把异步回调的碎片化程序重新组织成一个线性过程，同时还可以最大限度的保留异步回调结构带来的并发性能。

Above quotation only mentioned about the “coroutine” in the context of “callback functions”,
which is implemented in [Event Loop](#Event%20Loop).

(Except GO and Erlang? [Golang
是单线程模型还是多线程模型？](https://www.zhihu.com/question/268828178)) [GMP 模型 M 个协程绑定 1
个线程，是 N:1 和 1:1 类型的结合](https://learnku.com/articles/41728)

- [Erlang Scheduler Details and Why It Matters](https://hamidreza-s.github.io/erlang/scheduling/real-time/preemptive/migration/2016/02/09/erlang-scheduler-details.html)
- [How Erlang does scheduling](http://jlouisramblings.blogspot.com/2013/01/how-erlang-does-scheduling.html)

> Go 调度本质是把大量的 goroutine 分配到少量线程上去执行，并利用多核并行，实现更强大的并发。

A Go channel is a blocking queue (with select statements). [c++ 实现 blocking
queue](https://segmentfault.com/a/1190000021133490), [Go channel vs Java BlockingQueue](https://stackoverflow.com/questions/10690403/go-channel-vs-java-blockingqueue)

> 协程通常是纯软件实现的多任务，与CPU和操作系统通常没有关系，所以没有理论上限。

> 概括的说，进程是系统级的多任务，进程的切换，会带来大量内存的加载，所以很可能导致缺页中断，然后读硬盘加载相应数据到内存中。
而线程由于共享进程空间，所以就大大减少了进程切换的开销，但依然是一个系统调用，需要从用户空间切换到系统空间然后再切换回用户空间，同时有可能导致 CPU 将时间片切给其它进程。
而协程则是由编程语言所支持的在用户空间内的多任务，简单的说就是同一个线程中，系统维护一个程序列表，某个程序阻塞了，如果有其它程序等待执行，则不切线程而直接恢复那个程序的执行上下文。

> 说实话这两个没有任何本质的区别，说到底线程也好协程也把无非就是执行流的暂停与恢复，从现代操作系统的角度来看协程实现在用户态，线程实现在内核态。
为什么线程这么流行呢？因为线程这个概念把执行流管理的复杂度“封装”在了操作系统中，对程序员可不见，使用起来更加方便，但协程就不一样了，不是随随便便哪个程序员能在用户态搞定执行流管理的.

(until recently the appearance of asyncio in Python, coroutine in Kotlin,
Rust tokio and `async/await` in JavaScript)

(but the old days of Promise/A+ and Callback JavaScript is also some kind of coroutine? I guess)

What about ErlangVM's [virtual
thread](https://en.wikipedia.org/wiki/Green_threads) and Go's goroutine? [How
are Erlang processes different from Golang
goroutines?](https://www.quora.com/How-are-Erlang-processes-different-from-Golang-goroutines)

<https://stackoverflow.com/questions/1934715/difference-between-a-coroutine-and-a-thread>
[Callback 与 Coroutine 协程概念说明 ](https://aaaaaaron.github.io/2019/10/09/Callback%E4%B8%8E-Coroutine-%E5%8D%8F%E7%A8%8B%E6%A6%82%E5%BF%B5%E8%AF%B4%E6%98%8E/)

> With threads, the operating system switches running threads preemptively
according to its scheduler, which is an algorithm in the operating system
kernel. With coroutines, the programmer and programming language determine when
to switch coroutines; in other words, tasks are cooperatively multitasked by
pausing and resuming functions at set points, typically (but not necessarily)
within a single thread.

- <https://www.zhihu.com/question/449093278>

> C++20的那个coroutine其实不能算是个协程网络库，而是一个无栈协程标准，或者说一个编译器工具，是给写库的人用的。

- <https://stackoverflow.com/questions/27435284/multiprocessing-vs-multithreading-vs-asyncio-in-python-3>
- <https://leimao.github.io/blog/Python-Concurrency-High-Level/>

```python
if io_bound:
    if io_very_slow:
        print("Use Asyncio")
    else:
        print("Use Threads")
else:
    print("Multi Processing")
```

> A generator is a function, but instead of returning the return, instead
returns an iterator. (A Generator is an Iterator)

> The performance improvement from the use of generators is the result of the lazy (on demand) generation of values, which translates to lower memory usage.

```python
# Code from https://www.liaoxuefeng.com/wiki/1016959663602400/1017968846697824
## It's a generator. Or iterator? Or a function?
def consumer():
  r = ''
  while True:
    n = yield r
    if not n:
      return
    print('[CONSUMER] Consuming %s...' % n)
    r = '200 OK'

def produce(c): ## you know what? `c` is a callback function to me
    c.send(None) ## c.next()
    n = 0
    while n < 5:
        n = n + 1
        print('[PRODUCER] Producing %s...' % n)
        r = c.send(n) # Looks like a OBJECT?
        print('[PRODUCER] Consumer return: %s' % r)
    c.close()

c = consumer()
produce(c)
```

1. 執行到 `yield`，暫停。
2. 將執行權交給外部，等待外部 call `next`。
3. 外部 call `next`，回到步驟一繼續執行。

```javascript
const consumer_star = function* () {
  let r = ""
  while(true) {
    // consumer_star starts here in the second call of `next`. 
    // Read `val` from gen.next(val) then runs...
    // in the next time it reaches `yield`, `yield`(return) the value `r`
    n = yield r
    if (isNaN(n)) {
      return
    }
    console.log("[CONSUMER] Consuming", n)
    r = "200 OK"
  }
}

// https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Generator/next
// send(x) 对应于ES2015生成器对象中的 next(x)

const producer_g = (gen) => { 
  // 请注意，第一次调用没有记录任何内容，因为生成器最初没有产生任何结果。
  gen.next() // init, run consumer_star() until `yield`
  let n = 0
  while(n < 5) { 
    console.log("[PRODUCER] Producing", n)
    // send the value to consumer_star, coonsumer_star will run from `yield` until reach next `yield`
    let res = gen.next(n).value 
    console.log('[PRODUCER] Consumer return:',res)
    n++
  }
}

producer_g(consumer_star())

// what's the difference generator and callback?
// Generator provides a way to communicate between the generator and caller? 
const consumer = (n) => { 
  console.log(`[CONSUMER] Consuming`, n);
  return "200 OK"
}

const producer = (f) => { 
  let n = 0
  while(n < 5) { 
    console.log("[PRODUCER] Producing", n)
    let res = f(n)
    console.log('[PRODUCER] Consumer return:',res)
    n++
  }
}

producer(consumer)
```

- <https://medium.com/@adambene/async-await-vs-coroutines-vs-promises-eaedee4e0829>
- <https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/yield>
- <https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Operators/function>
- <https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Statements/function>
- <https://weihanglo.tw/posts/2017/javascript-concurrency-async-function/>

> Generators, also known as semicoroutines, are a subset of coroutines.

协程主要是让原来要使用”异步+回调方式”写出来复杂代码，简化成可以用看似同步的方式，这样我们就可以按串行的思维模式去组织原本分散在不同上下文的代码逻辑。也增强了程序的可读性。

- 进程：拥有自己独立的堆和栈，既不共享堆，也不共享栈，进程由操作系统调度；
- 线程：拥有自己独立的栈和共享的堆，共享堆，不共享栈，标准线程由操作系统调度；
- 协程：拥有自己独立的栈和共享的堆，共享堆，不共享栈，协程由程序员在协程的代码里调度。

## Async and multithread in Javascript

- <https://dev.to/bbarbour/if-javascript-is-single-threaded-how-is-it-asynchronous-56gd>

Javascript is a single threaded language. This means it has one call stack and
one memory heap. As expected, it executes code in order and must finish
executing a piece code before moving onto the next. It's synchronous, but at
times that can be harmful.

Well, we can thank the Javascript engine (V8, Spidermonkey, JavaScriptCore,
etc...) for that, which has *Web API that handle these tasks in the background*.
The call stack recognizes functions of the Web API and hands them off to be
handled by the browser. Once those tasks are finished by the browser, they
return and are pushed onto the stack as a callback.

- <https://thecodest.co/blog/asynchronous-and-single-threaded-javascript-meet-the-event-loop/>

Synchronous and Asynchronous in a Single and Multi-threaded Environment

- Synchronous with a single thread: Tasks are executed one after another. Each
task waits for its previous task to get executed.
- Synchronous with multiple threads: Tasks are executed in different threads but
wait for any other executing tasks on any other thread.
- Asynchronous with a single thread: Tasks start being executed without waiting
for a different task to finish. At a given time, only a single task can be
executed.
- Asynchronous with multiple threads: Tasks get executed in different threads
without waiting for other tasks to be completed and finish their executions
independently.

<https://www.zhihu.com/question/20866267>

> 异步机制是浏览器的两个或以上常驻线程共同完成的，例如异步请求是由两个常驻线程：JS执行线程和事件触发线程共同完成的，JS的执行线程发起异步请求（这时浏览器会开一条新的HTTP请求线程来执行请求，这时JS的任务已完成，继续执行线程队列中剩下的其他任务），然后在未来的某一时刻事件触发线程监视到之前的发起的HTTP请求已完成，它就会把完成事件插入到JS执行队列的尾部等待JS处理。又例如定时触发（settimeout和setinterval）是由浏览器的定时器线程执行的定时计数，然后在定时时间把定时处理函数的执行请求插入到JS执行队列的尾端（所以用这两个函数的时候，实际的执行时间是大于或等于指定时间的，不保证能准确定时的）。

浏览器每个页面就是一个独立的浏览器进程，这个进程包括独立与其他进程的JS线程。

> 但凡这种「既是单线程又是异步」的语言有一个共同特点：它们是 event-driven 的。驱动它们的 event 来自一个异构的平台。这些语言的 top-level 不象 C 那样是 main，而是一组 event-handler。虽然所有 event-handler 都在同一个线程内执行，但是它们被调用的时机是由那个驱动平台决定的。而且设计要求每个 event-handler 要尽快结束。未做完的工作可以通知那个异构的驱动平台来完成。所以那个驱动平台可以有许多线程。

<http://www.ruanyifeng.com/blog/2014/10/event-loop.html>

> JavaScript的主要用途是与用户互动，以及操作DOM。这决定了它只能是单线程，否则会带来很复杂的同步问题。比如，假定JavaScript同时有两个线程，一个线程在某个DOM节点上添加内容，另一个线程删除了这个节点，这时浏览器应该以哪个线程为准？

libuv库负责Node API的执行。它将不同的任务分配给不同的线程，形成一个Event Loop（事件循环），以异步的方式将任务的执行结果返回给V8引擎。

### Worker API

- <https://developer.mozilla.org/en-US/docs/Web/API/Worker/Worker>
- <https://deno.land/manual/runtime/workers>

### Event Loop

- <https://developer.mozilla.org/en-US/docs/Web/JavaScript/EventLoop>
- <https://html.spec.whatwg.org/multipage/webappapis.html#event-loops>
- <https://en.wikipedia.org/wiki/Event_loop>
- <https://blog.carbonfive.com/the-javascript-event-loop-explained/>
- <https://docs.python.org/3/library/asyncio-eventloop.html>
- <https://iximiuz.com/en/posts/explain-event-loop-in-100-lines-of-code/>

## Thread and Lock

The greatest weakness of the approach, however, is that threads-and-locks
programming is hard. It may be easy for a language designer to add them to a
language, but they provide us, the poor programmers, with very little help.

### Re-entrant Lock

- <https://en.wikipedia.org/wiki/Reentrancy_(computing)>

### Condition Variables

A condition variable is associated with a lock, and a thread must hold that lock
before being able to wait on the condition. Once it holds the lock, it checks to
see if the condition that it’s interested in is already true. If it is, then it
continues with whatever it wants to do and unlocks the lock.

### Atomic

Atomic variables are the foundation of non-blocking, lock-free algorithms, which
achieve synchronization without locks or blocking. If you think that programming
with locks is tricky, then just wait until you try writing lock-free code.

## FP

Immutability

> If It Hurts, Stop Doing It

The problem is that languages like Java make it both easy to write code with
hidden mutable state like this and virtually impossible to tell when it happens

```elixir
# aux function for word_count
defp wc_go([], map), do: map

defp wc_go([head | tail], map) do
  map = Map.update(map, Atom.to_string(head), 1, fn val -> val + 1 end) # & &1 + 1
  wc_go(tail, map)
end

@spec word_count([atom]) :: %{String.t() => non_neg_integer()}
def word_count(list) do
  map = %{}
  wc_go(list, map)
end

# reduce version
@spec reduce_word_count([atom]) :: %{String.t() => non_neg_integer()}
def reduce_word_count(list) do
  Enum.reduce(list, %{}, fn (elem, acc) ->
    Map.update(acc, Atom.to_string(elem), 1, fn val -> val + 1 end)
  end)
end
```

```clojure
(def sum (future (+ 1 2 3 4 5)))
```

Dereferencing a future will block until the value is available (or realized).

A promise is very similar to a future in that it’s a value that’s realized
asynchronously and accessed with `deref` or `@`, which will block until it’s
realized.  The difference is that creating a promise does not cause any code to
run—instead its value is set with deliver.

## Impure Clojure

A pure functional language provides no support for mutable data whatsoever.
Clojure, by contrast, is impure—it provides a number of different types of
concurrency-aware mutable variables, each of which is suitable for different use
cases.

## Actor

Functional programming avoids the problems associated with shared mutable
state by avoiding mutable state. Actor programming, by contrast, retains
mutable state but avoids sharing it.

An actor typically sits in an infinite loop, waiting for a message to arrive with
receive and then processing it.

```elixir
defmodule Counter do
  def loop(count) do
    receive do
      {:next} -> IO.puts("Current count: #{count}")
      loop(count + 1) # loop forever
    end
  end
  # With that in mind, instead of calling spawn() and
  # sending messages directly to an actor, it’s common practice to provide a set of
  # API functions

  @spec next(pid)::any
  def next(counter) do
    send(counter, {:next})
  end

  # counter = spawn(Counter, :loop, [1])
  @spec start(integer) :: pid
  def start(count \\ 1) do
    spawn(__MODULE__, :loop, [count]) # what the :loop atom is about
  end
end
```

As we’ve already seen, messages between actors are sent asynchronously—the
sender doesn’t block. But what happens if we want to receive a reply? What if,
for example, we want our Counter actor to return the next number rather than
just printing it?

```elixir
# 前略
  def loop(count) do
    receive do
      {:next, sender_pid, ref} -> send(sender_pid, {:ok, ref, count})
      loop(count + 1) # loop forever
    end
  end
  # With that in mind, instead of calling spawn() and
  # sending messages directly to an actor, it’s common practice to provide a set of
  # API functions

  @spec next(pid)::integer
  def next(counter) do
    # no idea why we need this, maybe it's for the uuid of message?
    # UUID could be a good alternative for references unless you are concerned
    # about the memory load: references are way more efficient.
    ref = make_ref() 
    send(counter, {:next, self(), ref})
    receive do
      {:ok, _, count} -> count
    end
  end
```

Elixir typically uses tuples as messages, where the first element indicates
success or failure.

In this instance, we also include a unique reference generated by the client,
which ensures that the reply will be correctly identified in the event that
there are multiple messages waiting in the client’s mailbox.

> If it’s a process that you created, this is easy, but how do you send
a message to a process that you didn’t create?

The most convenient is to register a name for the process

```elixir
pid = Counter.start(1)
Process.register(pid, :counter)
# pretend that you have no access to the pid
counter = Process.whereis(:counter)
# Or use Process.registered to see all the process
```

### GenServer

GenServer, a `behaviour` that allows us to automate the details of creating a
stateful actor.

A behaviour is very similar to an interface in Java. It defines a set of functions.
A module specifies that it implements a behaviour with `use`

Elixir uses variable names that start with an underscore (`_`) to indicate that
they’re unused.

[Alan Kays Definition Of Object Oriented](https://wiki.c2.com/?AlanKaysDefinitionOfObjectOriented)

> I’m sorry that I long ago coined the term "objects" for this topic because it gets
many people to focus on the lesser idea.  The big idea is "messaging" … The
Japanese have a small word [ma
(間)](https://ja.wikipedia.org/wiki/%E9%96%93_(%E4%BD%99%E7%99%BD%E9%83%A8%E5%88%86))
for "that which is in-between" perhaps the nearest English equivalent is
"interstitial." The key in making great and growable systems is much more to
design how its modules communicate rather than what their internal properties
and behaviors should be.

### Weakness

As with threads and locks, actors provide no direct support for parallelism.
Parallel solutions need to be built from concurrent building blocks, raising
the specter of nondeterminism. And because actors do not share state and
can only communicate through message passing, they are not a suitable
choice if you need fine-grained parallelism.

### Compare with CSP

Although CSP has surface similarities with actors, its emphasis on the channels
used for communication, rather than the entities between which communication
takes place, leads to it having a very different flavor.

## Mutually Recursive Function

[trampoline](https://clojuredocs.org/clojure.core/trampoline)
