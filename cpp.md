# C++ Note

## Books

- Understanding and Using C Pointers by Richard Reese
- K&R
- The C++ Programming Language, 4th Edition by Bjarne Stroustrup
- C++ Crash Course A Fast-Paced Introduction by Josh Lospinoso

## Static and Extern

https://www.geeksforgeeks.org/internal-linkage-external-linkage-c/

When you use the `static` keyword, you specify *internal linkage*. Internal
linkage means that a variable is inaccessible to other translation units. You
can alternately specify external linkage, which makes a variable accessible to
other translation units. For external linkage, you use the extern keyword
instead of static.

Funcation themselves are always external, because C does not allow function
to be defined inside other functions.

## Static Member

Like other static variables, static members have only a single instance.
All instances of a class with static members share the same member, so if
you modify a static member, all class instances will observe the modifica-
tion.

## Dynamic

Objects with dynamic storage duration are allocated and deallocated on request.
You have manual control over when a dynamic object’s life begins and when it
ends. Dynamic objects are also called allocated objects for this reason.

The primary way to allocate a dynamic object is with a new expression. A
new expression begins with the new keyword followed by the desired type of
the dynamic object.

```cpp
int* my_int_ptr = new int
```

Remember to use `delete`

The value contained in memory where the deleted object resided is
undefined. You would have to implement a custom
destructor to, for example, zero out some sensitive contents.

### Memory Leak

You must make sure that dynamic objects you allocate are also deallocated.

## Exception

Exceptions are types that communicate an error condition. When an error
condition occurs, you throw an exception. After you throw an exception, it’s
in flight. When an exception is in flight, the program stops normal execution
and searches for an exception handler that can manage the in-flight
exception. Objects that fall out of scope during this process are destroyed.

The other option for communicating error conditions is to return an
error code as part of a function’s prototype.

In situations where an error occurs that can be dealt with
locally or that is expected to occur during the normal course of a program’s
execution, you generally return an error code.

### Throw

Most objects are throwable.

But it’s good practice to use one of the
exceptions available in stdlib, such as `std::runtime_error` in the `<stdexcept>`
header.

### Try Catch

You use try- catch blocks to establish exception handlers for a block of code.
Within the try block, you place code that might throw an exception. Within
the catch block, you specify a handler for each exception type you can handle.

Exceptions use these inheritance relationships to determine whether a
handler catches an exception. Handlers will catch a given type and any of its
*parents’* types.

The rules for exception handling are based on class inheritance. When
an exception is thrown, a catch block handles the exception if the thrown
exception’s type matches the catch handler’s exception type or if the
thrown exception’s type *inherits from* the catch handler’s exception type.

#### Special handler

```cpp
try {
  throw 'z'; // Don't do this.
} catch (...) {
  // Panic; an unforeseen exception type was thrown
  // Handles any exception, even a 'z'
}
```

#### General handler

```cpp
try {
  // Code that might throw an exception
  // --snip--
} catch (const std::logic_error& ex) {
  // Log exception and terminate the program; there is a programming error!
  // --snip--
} catch (const std::runtime_error& ex) {
  // Do our best to recover gracefully
  // --snip--
} catch (const std::exception& ex) {
  // This will handle any exception that derives from std:exception
  // that is not a logic_error or a runtime_error.
// --snip--
} catch (...) {
  // Panic; an unforeseen exception type was thrown
  // --snip--
}
```

In a catch block, you can use the throw keyword to resume searching for an
appropriate exception handler. This is called rethrowing an exception.

### noexcept

The keyword noexcept is another exception-related term you should know.
You can, and should, mark any function that cannot possibly throw an
exception noexcept.

### Call Stack

The runtime seeks the closest exception handler to a thrown exception.
If there is a matching exception handler in the current stack frame, it will
handle the exception. If no matching handler is found, the runtime will
unwind the call stack until it finds a suitable handler.

As a general rule, treat destructors as if they were noexcept.

## Alternative to Exceptions

- you can manually enforce class invariants by exposing some method that communicates
whether the class invariants could be established
- return multiple values using structured binding declaration, a language feature that allows you to
return multiple values from a function call.

## Copy Sematics

They’re equivalent and independent.

Copying is extremely common, especially when passing objects to func-
tions by value.

A double free occurs when you deallocate an object twice.

Recall that once you’ve deallocated an object, its storage lifetime ends.
This memory is now in an undefined state, and if you destruct an
object that’s already been destructed, you’ve got undefined behavior.

There're two ways to copy an object.

### Copy Constructors

```cpp
struct SimpleString {
  // --snip--
  SimpleString(const SimpleString& other);
};

// which is called by
SimpleString a = "test";
SimpleString b = a;
// Or use uniform initialization
SimpleString b{a};
```

### Copy Assessment

This operator is called when an already initialized object is assigned a new value from another existing object.

```cpp
struct SimpleString {
  // --snip--
  SimpleString& operator=(const SimpleString& other) {
    if (this == &other) return *this;
    // --snip--
    return *this;
  }
}

// which is called by
SimpleString a = "test";
SimpleString b = "something else";
b = a;
```

Often, the compiler will generate default implementations for copy con-
struction and copy assignment. The default implementation is to invoke
copy construction or copy assignment on each of a class’s members.

Some classes simply cannot or should not be copied -- use `delete`

## Move Semantics

lvalue and rvalue.

You can communicate to the compiler that a function accepts lvalues or
rvalues using lvalue references (&) and rvalue references (&&).

rvalue is intermediate, temporary value, or a literal, created by a temp register.

```cpp
int main() {
  auto x = 1;
  ref_type(x); // lvalue
  ref_type(2); // rvalue
  ref_type(x + 2); // rvalue
}
```

Use `std::move` to force make a lvalue to rvalue.

https://stackoverflow.com/questions/2785612/c-what-does-the-colon-after-a-constructor-mean
https://www.geeksforgeeks.org/when-do-we-use-initializer-list-in-c/

## Interface

*Object composition* is a design pattern where a class contains members of
other class types.

An alternate, antiquated design pattern called *implementation inheritance* achieves runtime polymorphism.

*Implementation inheritance* allows you to build hierarchies of classes; each child inherits
functionality from its parents. Over the years, accumulated experience with
*implementation inheritance* has convinced many that it’s an anti-pattern.

Unfortunately, there’s no interface keyword in C++. You have to define
interfaces using antiquated inheritance mechanisms.

### Member inheritance

Derived classes inherit non-private members from their base classes.

Unfortunately, experience has convinced many in the programming community to avoid
member inheritance because it can easily yield brittle, hard to reason about
code compared to composition-based polymorphism. (This is why so many
modern programming languages exclude it.)

### virtual methods

Virtual function table

### pure virtual classes

- https://en.cppreference.com/w/cpp/language/abstract_class
- https://www.geeksforgeeks.org/pure-virtual-functions-and-abstract-classes/

in here pure virtual classes means abstract class. NOT the pure in functional programming.

You achieve interface inheritance through deriving from base classes that
contain only pure-virtual methods. Such classes are referred to as pure-virtual
classes.

To declare an interface, declare a pure virtual class. To implement an
interface, derive from it. Because the interface is pure virtual, an
implementation must implement all of the interface’s methods.

### Using interfaces

- Constructor: use references
- Properties: use pointer

## Template

compile-time polymorphism.

- template class
- template function

Instantiate Template.

Generally, you don’t have to provide template function parameters. The compiler can deduce them from usage.

### *Concepts*

- https://en.wikipedia.org/wiki/Concepts_(C%2B%2B)
- https://en.cppreference.com/w/cpp/language/constraints

*Concepts* constrain template parameters, allowing for parameter checking at the
point of instantiation rather than the point of first use.

By catching usage issues at the point of instantiation, the compiler can give you a friendly,
informative error code. for example, “You tried to instantiate this template
with a char*, but this template requires a type that supports multiplication.”

## Named Conversion Function

Recall that braced initialization doesn’t permit narrowing conversions.
Technically, the braced initializer is an explicit conversion.

### C-Style Cast

```c
(desired-type)object-to-cast
```

Just don't do it unless necessary.

### `const_cast`

get rid of `const` keyword, but why do you do it?

### `static_cast`

Attempting ill-defined conversions with static_cast, such as converting a `char*` to a `float*`.

### `reinterpret_cast`

Do whatever you want, just reinterpret the memory.

### User-Defined Type Conversion

In user-defined types, you can provide user-defined conversion functions.

```cpp
struct MyType {
  operator destination_type() const {
    // return a destination-type from here.
    // --snip--
  }
}
```

You can achieve explicit conversions with the explicit keyword.
Explicit constructors instruct the compiler not to consider the constructor
as a means for implicit conversion.

## Header

See C Programming: A Modern Approach 2nd Edition Chapter 15.

- https://stackoverflow.com/questions/5904530/how-do-header-and-source-files-in-c-work
- https://stackoverflow.com/questions/3482948/what-is-the-fundamental-difference-between-source-and-header-files-in-c

In particular, we aim for a clean separation of interfaces (e.g.,
function *declarations*) and implementations (e.g., function *definitions*).

For realistically sized applications, even having all of the user’s own code in a sin-
gle file is both impractical and inconvenient.

The declarations in a program consisting of many separately compiled parts must be consistent in exactly
the same way the declarations in a program consisting of a single source file must be.

`extern` would simply be ignored because a declaration with an initializer is
always a definition. An object must be defined exactly once in a program. It may
be declared many times, but the types must agree exactly.

Global variables are in general best avoided because they cause maintenance
problems. *Placing variables in a namespace helps a bit, but such variables are
still subject to data races.*

Unfortunately, spaces are significant within the `< >` or `" "` of an include directive

```cpp
#include < iostream > // will not find <iostream>
```

Conversely, a header should never contain:

- Ordinary function definitions
- Data definitions  `int a;`
- Aggregate definitions `short tbl[] = { 1, 2, 3 };`
- Unnamed namespaces
- `using`-directives

Use the header `dc.h` holds the declarations of every name used in more than one .cpp file.

This single-header style of physical partitioning is most useful when the program is small and
its parts are not intended to be used separately.

the one-definition rule ("the ODR")

There is no guaranteed order of initialization of global variables in different translation units.
Consequently, it is unwise to create order dependencies between initializers of global variables in
different compilation units.

**See 4.4 of The C Programming Language(K&R)** to find out how to solve scope-related problems.

Distingush between *declaration* and *definition*. A declaration announces the existence the properties of
a variable or function, but does not define it. A definition defines the properties of a variable and causes
storage to be allocated for it.

There must be only one definition of  an external variable among all the files that make up
the source program. Other files may contain extern declarations to access it.

## Function Pointer

See $A7.3.2 Function Calls in K&R.

- https://stackoverflow.com/questions/6893285/why-do-function-pointer-definitions-work-with-any-number-of-ampersands-or-as

> From the perspective of the computer, a function is just a memory address
which, if executed, performs other instructions. So a function in C is itself
modelled as an address, which probably leads to the design that a function is
"the same" as the address it points to.

```c
// a more moder style
void qsort(void *base, size_t left, size_t right, int (*compar)(const void *, const void *)); 
bool numcmp(const char *, const char *);
bool strcmp(const char *, const char *);
bool numeric = true;
// this line is blow my mind
// (int (*)(const void *, const void *)) (numeric ? numcmp : strcmp)
// Force cast the function `numcmp` or `strcmp` to `int (*)(const void *, const void *)`
// decide to use which by numeric or not. (maybe it can call another function)
// Since they are known to be functions
// & operator is not needed
qsort((void **) lineptr, 0, nlines-1, (int (*)(const void *, const void *)) (numeric ? numcmp : strcmp));
int (* comp)(void *, void *);
```

`comp` is a *pointer* to a function that takes two `void *` arguments and
returns an `int`. (it should be `boolean` in modern C.) But this example is from *K&R* so be it.

```c
(* comp)(v[i], v[left]);
comp(v[i], v[left]);
```


[32 years on, K&R's "The C Programming Language" still stands alone](https://news.ycombinator.com/item?id=1245255)

There are some program converting the signature of C to plain English. See [dcl](https://stackoverflow.com/questions/20170068/kr-c-chapter-5-dcl-issue), [cdecl - the C gibberish to English gibberish translato](https://cdecl.org/), [Reddit](https://www.reddit.com/r/programming/comments/a3pm6/cdecl_the_c_gibberish_to_english_translator_is/)

is to call it and they are equivalent. (See [here](https://stackoverflow.com/questions/1952175/how-can-i-call-a-function-using-a-function-pointer))

The generic pointer type `void *`. Any pointer can be cast to `void *` and back
again without loss of information. Previously, `char *` pointers played the role of generic pointers.

### STL

The facilities of the standard library are presented through a set of standard headers
No suffix is needed for standard-library headers; they are known to be headers because they are
included using the #include<...> syntax rather than #include"...".

For each C standard-library header `<X.h>`, there is a corresponding standard C++ header `<cX>`.

## Mix C and C++

When mixing C and C++ code fragments in one program, we sometimes want to pass pointers to
functions defined in one language to functions defined in the other. See $15.2.6 of The C++ Programming Language.

## Static

When used in namespace scope (including the global scope; ), the keyword static
(somewhat illogically) means "not accessible from other source files" (i.e.,
internal linkage).

## Inline

- https://en.wikipedia.org/wiki/Inline_function
- https://stackoverflow.com/questions/1137575/inline-functions-vs-preprocessor-macros
- https://stackoverflow.com/questions/5226803/inline-function-v-macro-in-c-whats-the-overhead-memory-speed

Like function macro. Just simple substitution to avoid a function call and its overhead.

## Constant Expressions

Constant expressions are expressions that can be evaluated at compile time.
For performance and safety reasons, whenever a computation can be done
at compile time rather than runtime, you should do it.

## Volatile

The volatile keyword tells the compiler that every access made through
this expression must be treated as a visible side effect. This means access
cannot be optimized out or reordered with another visible side effect.

The volatile keyword indicates that a method can be invoked on volatile objects.
This is analogous to how const methods can be applied to const objects.

## Statement

See C++ Crash Course Chapter 8.

An expression statement is an expression followed by a semicolon (;).

### Declaration Statements

Declaration statements (or just declarations) introduce identifiers, such as
functions, templates, and namespaces, into your programs.

### Functions

A function declaration, also called the function’s signature or prototype, specifies
a function’s inputs and outputs.

Functions that aren’t member functions are called non-member functions,
or sometimes free functions, and they’re always declared outside of main()
at namespace scope.

You can use declared functions throughout your code as long as they’re
eventually defined somewhere. Your compiler toolchain can figure it out.

## Structured Bindings

```cpp
auto [object-1, object-2, ...] = plain-old-data;
```

## Structure

POD (plain old data) is a equivalence of struct in C.

`struct mystrcut` is not equivalent to `mystrcut` (the type name) in C. It's often the case that
use them `typedef` to make them equivalent.

> Personally, I prefer to omit the typedef and refer to the type as struct bar. The typedef saves a little typing, but it hides the fact that it's a structure type.

- https://stackoverflow.com/questions/1675351/typedef-struct-vs-struct-definitions

Don't assume that the size of a structure is the sum of the size of its members,
because of alignment requirements for different objects, there may be unnamed "holes" in the structure.

Use `sizeof` to get the size of a structure.

## Union

- https://stackoverflow.com/questions/346536/difference-between-a-structure-and-a-union

Just like type Union. You should only use one of its "member" at the same time.
