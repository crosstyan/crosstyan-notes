# Haskell

## Installation

https://wiki.archlinux.org/title/haskell

```bash
sudo pacman -S ghc ghc-static stack hlint
```

## Usage

```bash
runghc hello.hs
ghc hello.hs
```

## Lambda Calculus

Lambda calculus is your foundation,
because Haskell is a lambda calculus.

### Pure

The word *purity* in functional programming is sometimes
also used to mean what is more properly called referential
transparency. *Referential transparency* means that the same
function, given the same values to evaluate, will always return
the same result in pure functional programming, as they do
in math.

### Terms

- The word expression refers to a superset of all those things: an expression
can be a variable name, an abstraction, or a combination of
those things.
- An abstraction is a function. It is a lambda term that has a
head (a lambda) and a body and is applied to an argument. An
argument is an input value.

the lambda abstraction $\lambda x.x$ has no name. It's an anonymous function.

The dot (.) separates the parameters of the lambda from
the function body.

Each lambda can only bind *one parameter* and can only accept
*one argument*. Functions that require multiple arguments
have multiple, nested heads.

$$\lambda xy.xy \tag*{ (Invalid Expresstion)}$$

```typescript
(x:number, y:number) => x * y 
```

```clojure
(fn [x y] (x * y))
```

```elixir
fn (x, y) -> x * y end
```

$$\lambda x.(\lambda y.xy) \tag*{ (Valid Expresstion)}$$

```typescript
(x:number)=> (y:number) => x * y
const multiply = (x:number) => (y:number) => x * y
const SIX = multiply(2)(3)
const multiply_by_two = multiply(2)
const six = multiply_by_two(3)
```

```clojure
(fn [x] (fn [y] (x * y)))
```

```elixir
fn (x) -> fn (y) -> x * y end end
f = fn (x) -> fn (y) -> x * y end end
# elixir separates anynomous functions and normal function with a dot
f.(2).(3) 
# I don't know why such a weird syntax is used
# https://stackoverflow.com/questions/18011784/why-are-there-two-kinds-of-functions-in-elixir
```

*Beta normal form* is when you cannot beta reduce (apply lambdas to
arguments) the terms any further.

*Divergence* here means that the reduction process never terminates or ends.
Reducing terms should ordinarily *converge* to beta normal form, and divergence is
the opposite of convergence, or normal form.

## GHCi

Defining functions in a Haskell source code file and in GHCi are a little
different. To introduce definitions of values or functions in GHCi, you must use
*let*.

This has changed as of the release of GHC 8.0.1; using *let* in declarations in
GHCi is no longer necessary.

`GHCi` command.

```haskell
:info
:quit
:m
:load
:reload
:type
```

## Infix Operators

```haskell
1 + 2
-- lisp like
(+) 1 2

div 4 2
4 `div` 2 -- backtick
```

If the function name is alphanumeric, it is a prefix function
by default.

If the function name is symbol, it is infix by default but can be made
prefix by wrapping it in parentheses.

Use `:info` or `:i` to check a function in `GHCi`.

```haskell
infixr 8 ^
infixl 7 *
infixl 6 -
```

`7` is the precedence: higher is applied first, on a scale of
0-9.

## Space

One thing to keep in mind is that indentation of
Haskell code is significant and can change the meaning of the
code. Incorrect indentation of code can also break your code.

Use spaces, not tabs, to indent your source code.

```haskell
foo x = let y = x + 1
            z = x - 1
        in y * z
```

```clojure
(defn foo [x]
  (let [y (+ x 1)
        z (- x 1)]
    (* y z)))
```

## sectioning

```haskell
(+1) 2 -- = 3
(2^) 4 -- = 16
(^4) 3 -- = 81
(/2) 1 -- = 0.5
(2/) 1 -- = 2
(1-) 100 -- = -99
(-1) 100 -- won't work since it doesn't know if (-1) is a function or a literal negative number
-- You can use sectioning for subtraction, but it must be the first argument
```

```clojure
((fn [x] (+ x 1)) 2) ;; 3
(#(Math/pow 2 %1) 4) ;; 16
(#(Math/pow %1 4) 3) ;; 81
(#(/ %1 2) 1)
(#(/ 2 %1) 1)
(#(- 1 %1) 100)
(#(- %1 1) 100)
```

```js
(x => x + 1)(2)
(x => Math.pow(2,x))(4)
(x => Math.pow(x,4))(3)
// ...
```

You get the idea.

Scope is the area of source code where a binding of a variable applies.

In repl

```haskell
let x = 5; y = 6 in x * y
```

```haskell
-- mult1 is Integer
mult1     = x * y
  where x = 5
        y = 6
```

```clojure
(def mult1
  (let [x 5
        y 6]
    (* x y)))
```

In Haskell, it is a special type, called `IO`,
used when the result of running the program involves effects
beyond evaluating a function or expression.

We used `::` to declare the types of each top-level expression.
It isn’t strictly necessary, as the compiler can infer these types,
but it is a good habit to be in as you write longer programs.


```haskell
concat :: [[a]] -> [a]
```

It will have the same values inside it
as the list of lists did; it just flattens it into one list structure, in
a manner of speaking.

```haskell
(++) :: [a] -> [a] -> [a]
```

1. Take an argument of type `[a]`. This type is a list of elements of some type
`a`. This function does not know what type `a` is. It doesn’t need to know. In
the context of the program, the type of `a` will be known and made concrete at
some point.
2. Take another argument of type `[a]`, a list of elements whose type we don’t
know. Because the variables are the same, they must be the same type throughout
(`a == a`).
3. Return a result of type `[a]`.

The infix operator, (`!!`), returns the element that is in the
specified position in the list.

The same as `nth` in clojure

`take` is the same as clojure. Return first `n`.

`drop` drop first two value. Return the rest.

`head` first element, `tail` is the rest.

use the `$` symbol to avoid using parentheses.

```haskell
drop 4 (drop 5 "Curry is awesome")
drop 4 $ drop 5 "Curry is awesome"
```

## Type

> Another way, which ML
advocates, is to install
some means of
understanding in the very
programs themselves.

The value `1 / 2 :: Rational` will be a
value carrying two Integer values, the numerator 1 and the
denominator 2, and represents a ratio of 1 to 2.

Num is a *typeclass* for which most numeric types will
have an instance because there are standard functions that are
convenient to have available for all types of numbers.

`127::Int8`

### If

```haskell
if <Bool> then <t> else <f>
```

```clojure
(if <Bool>
  <t>
  <f>)
```

### Tuple

The *two-tuple* is expressed at both the type level and
term level with the constructor `(,)`.

```haskell
fst :: (a, b) -> a
snd :: (a, b) -> b
```

In Haskell there are seven categories of entities that have
names: 

- functions 
- term-level 
- variables
- data constructors
- type variables
- type constructors
- typeclasses
- modules

Type variables (that is, variables in type signatures) generally start at *a*
and go from there: *a, b, c* and so forth.

Functions can be used as arguments and in that case are
typically labeled with variables starting at *f*. (*f, g, h*)
or f' (f prime).

If you have a list of things you have named *x*, by convention
that will usually be called *x*, that is, the plural of *x*.

### ->

The arrow, (->), is the type constructor for functions in Haskell.

The arrow is an infix operator that has two parameters and associates to the
right (although function application is left associative).

The parameterization suggests that we will apply the function to some argument
that will be bound to the first parameter, with the second parameter, *b*,
representing the return or result type.

### Typeclass-constrained type variables

Instead of limiting this function to a concrete type, we
get a typeclass-constrained polymorphic type variable.

```haskell
(/) :: Fractional a => a -> a -> a
(+) :: Num a => a -> a -> a
```

We say it’s constrained because we still don’t know the concrete type of `a`,
but we do know it can only be one of the types that has the required typeclass
instance.

### Currying

all functions in Haskell take one argument and return one result.

Currying refers to the nesting of multiple functions, each accepting one
argument and returning one result, to allow the illusion of multiple-parameter
functions.

#### right associative.

```haskell
f:: a -> a -> a
-- equals
f:: a -> (a -> a)
```

Remember, when we have a lambda expression that appears
to have two parameters, they are nested lambdas.

#### Application is evaluation

in other words, the only way to evaluate anything is by applying functions, and
function application is left associative.

### Uncurrying

If you uncurry `(+)`, the type changes from `Num a => a -> a -> a` to 
`Num a => (a, a) -> a` (the latter is a tuple as parameter)

- Uncurried functions: One function, many arguments
- urried functions: Many functions, one argument apiece

## Polymorphism

Broadly speaking, type signatures may have three kinds of types: concrete,
constrained polymorphic, or parametrically polymorphic.

Recall that when you see a lowercase name in a type signature, it is a type
variable and polymorphic (like *a*, *t*, etc). If the type is capitalized, it
is a specific, concrete type such as `Int`, `Bool`, etc.

Concrete types have even more flexibility in terms of computation. This has to
do with the additive nature of typeclasses.  For example, an *Int* is only an
*Int*, but it can make use of the methods of the *Num* and *Integral*
typeclasses because it has instances of both.

In sum, if a variable could be *anything*, then there’s little that can be done
to it because it has no methods. If it can be *some* types (say, a type that has
an instance of Num), then it has some methods. If it is a concrete type, you
lose the type flexibility but, due to the additive nature of typeclass
inheritance, gain more potential methods.

```haskell
length [1, 2, 3] :: Int
(/) :: Fractional a => a -> a -> a
-- error. Need to convert Int to Fractional (or Num) to solve it
6 / length [1, 2, 3] 

fromInteger :: Num a => Integer -> a
```

## Typeclass

```haskell
ghci> :info Bool
type Bool :: *
data Bool = False | True
  	-- Defined in ‘GHC.Types’
instance Eq Bool -- Defined in ‘GHC.Classes’
instance Ord Bool -- Defined in ‘GHC.Classes’
instance Enum Bool -- Defined in ‘GHC.Enum’
instance Show Bool -- Defined in ‘GHC.Show’
instance Read Bool -- Defined in ‘GHC.Read’
instance Bounded Bool -- Defined in ‘GHC.Enum’
```

All members of `Ord` must be members of `Eq`, and all members of `Enum` must be
members of `Ord`. 

To be able to put something in an enumerated list, they must be able to be
ordered; to be able to order something, they must be able to be compared for
equality.

You can use a search engine like [Hoogle](http://haskell.org/hoogle) to find
information on Haskell datatypes and typeclasses.

### Eq

```haskell
type Eq :: * -> Constraint
class Eq a where
  (==) :: a -> a -> Bool
  (/=) :: a -> a -> Bool
  {-# MINIMAL (==) | (/=) #-}
  	-- Defined in ‘GHC.Classes’
```

First, it tells us we have a typeclass called Eq where there are
two basic functions, equality and nonequality, and gives their
type signatures.

```haskell
-- partial list
instance Eq a => Eq [a]
instance Eq Ordering
instance Eq Int
instance Eq Float
instance Eq Double
instance Eq Char
instance Eq Bool
instance (Eq a, Eq b) => Eq (a, b)
instance Eq ()
instance Eq a => Eq (Maybe a)
instance Eq Integer
```

We could’ve had GHC generate one for us using deriving `Eq`
or we could’ve written one, but we did neither, so none exists
and it fails at compile time.

```haskell
data Month = Jan | Feb | Mar | Apr | May | Jun | Jul | Aug | Sept | Oct | Nov | Dec

instance Eq Month where
  Jan == Jan = True
  Feb == Feb = True
  Mar == Mar = True
  Apr == Apr = True
  May == May = True
  Jun == Jun = True
  Jul == Jul = True
  Aug == Aug = True
  Sept == Sept = True
  Oct == Oct = True
  Nov == Nov = True
  Dec == Dec = True
  (==) _ _ = False

data Date = Date Month Int

instance Eq Date where
  (==) (Date m d) (Date m' d') = (==) m m' && (==) d d'
```

We’ve mentioned partial application of functions previously, but the term
**partial function** refers to something different. A partial function is one
that doesn’t handle all the possible cases, so there are possible scenarios in
which we haven’t defined any way for the code to evaluate.

```haskell
-- the second Pair is something like keyword
data Tuple a b = Tuple a b

instance (Eq a, Eq b) => Eq (Tuple a b) where
  (==) (Tuple a b) (Tuple a' b') = (==) a a' && (==) b b'

data EitherOr a b = Hello a | Goodbye b
instance (Eq a, Eq b) => Eq (EitherOr a b) where
  (==) (Hello a) (Hello a') = (==) a a'
  (==) (Goodbye b) (Goodbye b') = (==) b b'
  (==) (Hello a) (Goodbye b) = False
  (==) (Goodbye b) (Hello a) = False

-- Anything start with Hello or Goodbey is an instance of EitherOr
-- Hello 1 == Hello 1 -- -> True
```

Haskell manages effects by separating effectful
computations from pure computations in ways that preserve
the predictability and safety of function evaluation.
Effect-bearing computations themselves become more
composable and easier to reason about.

The benefits of explicit effects include the fact that it makes it relatively
easy to reason about and predict the results of our functions.

The `()` denotes an empty tuple, which we refer to as *unit*. Unit
is a value, and also a type that has only this one inhabitant,
that essentially represents nothing.



