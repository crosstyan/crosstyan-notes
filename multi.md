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

## Why?

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

## History

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

- https://putridparrot.com/blog/threads-promises-futures-async-c/
- https://stackoverflow.com/questions/17729924/when-to-use-promise-over-async-or-packaged-task
- http://jakascorner.com/blog/2016/03/promise-difference.html
- https://nikhilm.github.io/uvbook/basics.html
- https://stackoverflow.com/questions/62105010/i-have-a-question-about-c-boost-asio-and-std-async
- https://think-async.com/Asio/AsioAndBoostAsio.html
- https://stackoverflow.com/questions/27435284/multiprocessing-vs-multithreading-vs-asyncio-in-python-3

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

### CSP

The idea of CSP is simple: if there’s no shared data, each thread can be
reasoned about entirely independently, purely on the basis of how it behaves in
response to the messages that it received.

Each thread is therefore effectively a state machine: when it receives a
message, it updates its state in some manner and maybe sends one or more
messages to other threads, with the processing performed depending on the
initial state.

## Async and multithread

https://dev.to/bbarbour/if-javascript-is-single-threaded-how-is-it-asynchronous-56gd

Javascript is a single threaded language. This means it has one call stack and
one memory heap. As expected, it executes code in order and must finish
executing a piece code before moving onto the next. It's synchronous, but at
times that can be harmful.

Well, we can thank the Javascript engine (V8, Spidermonkey, JavaScriptCore,
etc...) for that, which has *Web API that handle these tasks in the background*.
The call stack recognizes functions of the Web API and hands them off to be
handled by the browser. Once those tasks are finished by the browser, they
return and are pushed onto the stack as a callback.

https://thecodest.co/blog/asynchronous-and-single-threaded-javascript-meet-the-event-loop/

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

https://www.zhihu.com/question/20866267

> 异步机制是浏览器的两个或以上常驻线程共同完成的，例如异步请求是由两个常驻线程：JS执行线程和事件触发线程共同完成的，JS的执行线程发起异步请求（这时浏览器会开一条新的HTTP请求线程来执行请求，这时JS的任务已完成，继续执行线程队列中剩下的其他任务），然后在未来的某一时刻事件触发线程监视到之前的发起的HTTP请求已完成，它就会把完成事件插入到JS执行队列的尾部等待JS处理。又例如定时触发（settimeout和setinterval）是由浏览器的定时器线程执行的定时计数，然后在定时时间把定时处理函数的执行请求插入到JS执行队列的尾端（所以用这两个函数的时候，实际的执行时间是大于或等于指定时间的，不保证能准确定时的）。

浏览器每个页面就是一个独立的浏览器进程，这个进程包括独立与其他进程的JS线程。

> 但凡这种「既是单线程又是异步」的语言有一个共同特点：它们是 event-driven 的。驱动它们的 event 来自一个异构的平台。这些语言的 top-level 不象 C 那样是 main，而是一组 event-handler。虽然所有 event-handler 都在同一个线程内执行，但是它们被调用的时机是由那个驱动平台决定的。而且设计要求每个 event-handler 要尽快结束。未做完的工作可以通知那个异构的驱动平台来完成。所以那个驱动平台可以有许多线程。

http://www.ruanyifeng.com/blog/2014/10/event-loop.html

> JavaScript的主要用途是与用户互动，以及操作DOM。这决定了它只能是单线程，否则会带来很复杂的同步问题。比如，假定JavaScript同时有两个线程，一个线程在某个DOM节点上添加内容，另一个线程删除了这个节点，这时浏览器应该以哪个线程为准？

> libuv库负责Node API的执行。它将不同的任务分配给不同的线程，形成一个Event Loop（事件循环），以异步的方式将任务的执行结果返回给V8引擎。
