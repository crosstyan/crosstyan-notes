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
-- mult1 is an Integer
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

Refer to [Custom Datatype](#Custom%20Datatype)

```haskell
ghci> :info Bool
type Bool :: *
data Bool = False | True -- Defined in ‘GHC.Types’
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

### Show

```haskell
type Show :: * -> Constraint
class Show a where
  showsPrec :: Int -> a -> ShowS
  show :: a -> String
  showList :: [a] -> ShowS
  {-# MINIMAL showsPrec | show #-}
```

### Read

Where Show takes things and turns them into human-readable strings, Read takes
strings and turns them into things.

```haskell
ghci>  :t read
read :: Read a => String -> a
```

There’s no way `Read a => String -> a` will always work.

Typeclasses are dispatched by type, but it’s an important thing to understand.

Typeclasses are defined by the set of operations and values all instances will
provide.

Typeclass *instances* are unique pairings of the typeclass and a type. They define
the ways to implement the typeclass methods for that type.

- a typeclass defines a set of functions and/or values;
- types have instances of that typeclass;
- the instances specify the ways that type uses the functions of the typeclass.

## Function

### Anonymous Functions

```haskell
(\x -> x + 1)
-- \ is the lambda character
```

Anonymous function with two arguments is also possible.

```haskell
(\x y -> x + y) -- will work
(\x -> \y -> x + y) -- the same
```

```clojure
(fn [x] (+ x 1))
#(+ %1 1)
```

```js
x => x + 1
```

```python
lambda x: x + 1
```

```elixir
fn (x) -> x + 1 end
```

```scala
(_ + 1)
(i:Int) => i + 1
```

```cpp
[] (int x) -> int { return x + 1; }
```

You get the idea. I don't like python version, by the way.
Because it can only has one line.

### Pattern Matching

See clojure, scala and elixir.

### Case expressions

```haskell
funcZ x = if x + 1 == 1 then "AWESOME" else "wut"

funcZ' x =
  case x + 1 == 1 of
    True -> "AWESOME"
    False -> "wut"
```

### Guard

```haskell
myAbs x
  | x < 0 = negate x
  | otherwise = x
```

Starting a new line and using the `pipe` `|` to begin a guard case.

`otherwise :: Bool = True`

### Function composition

```haskell
ghci> :i (.)
(.) :: (b -> c) -> (a -> b) -> a -> c  -- Defined in ‘GHC.Base’
infixr 9 .
```

$$(f \cdot g)(x) = f(g(x))$$

```haskell
(f . g) x = f (g x)
```

### Pointfree style

Not the pointer in C. No idea why is this important.

Pointfree refers to a style of composing functions without specifying their
arguments.

### Practice

```haskell
tenDigit x = snd $ divMod noLastDigit 10
  where noLastDigit = fst $ divMod x 10

hndDigit x = snd $ divMod noLastDigit 10
  where noLastDigit = fst $ divMod x 100

foldBool :: a -> a -> Bool -> a
foldBool x y b = if b then y else x

foldBool0 :: a -> a -> Bool -> a
foldBool0 x y b = case b of
                  False -> x
                  True -> y

foldBool1 :: a -> a -> Bool -> a
foldBool1 x y b = case (x, y, b) of
                  (x, _, False) -> x
                  (_, y,  True) -> y

foldBool2 :: a -> a -> Bool -> a
foldBool2 x y b
  | not b = y     -- b == False
  | b = x         -- b == True
  | otherwise = x -- Just please the compiler

foldBool3 :: a -> a -> Bool -> a
foldBool3 x _ False = x
foldBool3 _ y True  = y

g :: (a -> b) -> (a, c) -> (b, c)
g f (a, c) = (f a, c)
```

## Recursion

Where function composition as we normally think of it is
static and definite, recursive compositions are indefinite.

```haskell
applyTimes :: (Eq a, Num a) => a -> (b -> b) -> b -> b
applyTimes 0 f b = b
applyTimes n f b = f (applyTimes (n-1) f b)

incTimes :: (Eq a, Num a) => a -> a -> a
incTimes times n = applyTimes times (+1) n
```

### Botton

$\bot$ in Haskell is bottom. Logic `False`

Use Maybe

### Fabonacci

>In Haskell, the function call model is a little different, function calls might
not use a new stack frame, so making a function tail-recursive typically isn't
as big a deal—being productive, via guarded recursion, is more usually a
concern.  -[Tail recursion](https://wiki.haskell.org/Tail_recursion)

```haskell
fibonacci :: Integral a => a -> a
fibonacci 0 = 0
fibonacci 1 = 1
fibonacci x = fibonacci(x - 1) + fibonacci (x - 2)

factorial :: Integral a => a -> a
factorial 1 = 1
factorial n = n * factorial (n - 1)
```

```elixir
# from https://riptutorial.com/elixir/example/10847/pattern-matching

defmodule Math do
  def factorial(0): do: 1
  def factorial(n): do: n * factorial(n - 1)
end
# Math.factorial(5)
```

The magic is pattern matching

```clojure
;; No tail recursion optimization
;; It looks beautiful at least

;; Need a package to support pattern matching
(require '[clojure.core.match :refer [match]])

;; http://www.howardism.org/Technical/Clojure/tail-recursion.html
(defn factorial [n]
  (match [n]
         [1] 1
         [n] (*' n (factorial (- n 1)))))

(defn fibonacci [n]
  (match [n]
         [0] 0
         [1] 1
         [n] (+ (fibonacci (- n 2)) (fibonacci (- n 1)))))
```

I only show the language that supports pattern matching. It makes
recursion look beautiful. Of course, it's not tail recursion and
you can use other conditional statements like `if` or `switch` to
make it happen.

#### Go functions

https://en.wikibooks.org/wiki/Haskell/Recursion

```haskell
factorial n = go n 1
    where
    go n res
        | n > 1     = go (n - 1) (res * n)
        | otherwise = res
```

`go` is an auxiliary function which actually performs the factorial calculation.
It takes an extra argument, `res`, which is used as an *accumulating* parameter to
build up the final result.

You can call it other name though, it just a convention.

### Type keyword

https://joelburget.com/data-newtype-instance-class/

The type keyword, instead of the more familiar data or
newtype, declares a type synonym, or type alias.

1. To signal intent: using newtype makes it clear that you only intend for it to
be a wrapper for the underlying type. The newtype cannot eventually grow into a
more complicated sum or product type, while a normal datatype can.
2. To improve type safety: avoid mixing up many values of the same
representation, such as Text or Integer.
3. To add diﬀerent typeclass instances to a type that is otherwise unchanged
representationally, such as with Sum and Product.

## List

`x:xs` head and tails.

```haskell
safeTail:: [a] -> Maybe [a]
safeTail []= Nothing
safeTail (x:[]) = Nothing
safeTail (_:xs) = Just xs
```

### List comprehension

List comprehensions are a means of generating a new list
from a list or lists.

```haskell
[x^2 | x <- [1..10]]
[x^2 | x <- [1..10], rem x 2 == 0]
[x^y | x <- [1..5], y <- [2, 3]] -- it's gets even more interesting
[x | x <- "Three Letter Acronym", elem x ['A'..'Z']] -- "TLA"
```

```clojure
(map #(Math/pow %1 2) (range 1 11)) ;; range end is exclusive

(for [x (range 1 11)]
  (Math/pow x 2))

;; https://practical.li/clojure/thinking-functionally/list-comprehension.html
;; can also use `for`
(->> (range 1 11)
     (map #(Math/pow %1 2))
     (filter #(= (rem (int %1) 2) 0)))
     ;; must use int to convert float to int
     ;; or (= 0.0 0) will be false
     ;; #(even? (int %1)) should work too
(for [x (range 1 11)
      :let [y (Math/pow x 2)]
      :when (#(even? (int %1)) y)]
  y)

;; http://www.learningclojure.com/2013/02/for-vs-map.html
;; an ugly way to do it. 
(for [x (range 1 6)
      y [2 3]]
  (Math/pow x y))

;; doseq is only for side effect stuff.
```

```js
// https://dev.to/ycmjason/how-to-create-range-in-javascript-539i
// const range = [...Array(end - start + 1).keys()].map(x => x + start)
const range = len => [...Array(len).keys()] // A trick to get a list of 0 to len-1
range(10).map(x => Math.pow(x, 2)) // not quite right but you get the idea
range(10).map(x => Math.pow(x, 2)).filter(x => x % 2 === 0)
// https://blog.jayway.com/2020/10/20/list-comprehensions-in-javascript/
// no list comprehension in JavaScript
```

```python
# https://docs.python.org/3/tutorial/datastructures.html#list-comprehensions
# Python has list comprehension which is beautiful.

[x**2 for x in range(1, 11)]
[x**2 for x in range(1, 11) if x % 2 == 0]
[x**y for x in range(1, 6) for y in [2, 3]]
```

### Spine

When we talk about data structures in Haskell, particularly lists, sequences,
and trees, we talk about them having a *spine*. This is the connective structure
that ties the collection of values together.

You’ll see the term ‘spine’ used in reference to data struc-
tures, such as trees, that aren’t lists. In the case of a list, the
spine is a linear succession of one cons cell wrapping another
cons cell.

### map

- https://www.quora.com/What-is-the-difference-between-map-and-fmap-in-Haskell-1
- https://stackoverflow.com/questions/6824255/whats-the-point-of-map-in-haskell-when-there-is-fmap

```haskell
map :: (a -> b) -> [a] -> [b]

type Functor :: (* -> *) -> Constraint
class Functor f where
  fmap :: (a -> b) -> f a -> f b

fmap :: Functor f => (a -> b) -> f a -> f b -- what the functor is?
```

```haskell
map (^2) [1..10]
```

```haskell
map (+1) [1, 2, 3] -- -->
map (+1) (1 : (2 : (3 : []))) -- -->
(+1) 1 :
  map (+1)
    (2 : (3 : [])) -- --> apply to all elements

(+1) 1 :
  ((+1) 2 :
    ((+1) 3 : [])) -- --> lazy evaluation. reduced until evaluated.
```

Mystery expression

```haskell
itIsMystery xs = map (\x -> elem x "aeiou") xs

-- hlint reduce to it. should be more clear now
itIsMystery :: [Char] -> [Bool]
itIsMystery = map (flip elem "aeiou")

-- convert an String to boolean list. if the char is in "aeiou", the corresponding element is true.

isAeiou :: Char -> Bool
isAeiou = (`elem` "aeiou") -- this is backtick
isAeiou = (\x -> elem x "aeiou")
```

### filter and zip

We all know filter.

```haskell
-- clojure can pass multiple arguments to map, treating them like like zip!
zip :: [a] -> [b] -> [(a, b)]
zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]

zipWith (+) [1, 2, 3] [10, 11, 12] -- [1+10, 2+11, 3+12]
```

```haskell
myZip :: [a] -> [b] -> [(a, b)]
myZip (x:xs) (y:ys) = (x, y):myZip xs ys
myZip _ _ = [] -- including x:[] or y:[]

myZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
myZipWith f (x:xs) (y:ys) = f x y:myZipWith f xs ys
myZipWith _ _ _ = []
-- https://stackoverflow.com/questions/5776322/zip-function-in-haskell
```

## Fold

Folds as a general concept are called catamorphisms.

Catamorphisms are a means of deconstructing data. If the spine of a list is the
structure of a list, then a fold is what can reduce that structure.

```haskell
type Foldable :: (* -> *) -> Constraint
class Foldable t where
  foldr :: (a -> b -> b) -> b -> t a -> b
```

Where `map` applies a function to each member of a list and returns a list, a `fold`
replaces the cons constructors with the function and reduces the list.

It's `reduce` in other languages.

```haskell
sum :: Num a => [a] -> a
sum []     = 0
sum (x:xs) = x + sum xs

length :: [a] -> Integer
length []     = 0
length (_:xs) = 1 + length xs

product :: Num a => [a] -> a
product []    = 1
product (x:xs) = x * product xs

concat :: [[a]] -> [a]
concat []     = []
concat (x:xs) = x ++ concat xs

-- Hlint Suggestion: Use foldr. Replace with: myProduct xs = foldr (*) 1 xs
```

We call foldr the “right fold” because the fold is right associative;

```haskell
foldr (+) 0 [1,2,3]
```

```clojure
(reduce + 0 [1 2 3])
```

```js
[1, 2, 3].reduce((a, b) => a + b)
```

```python
import functools
functools.reduce(lambda a, b: a + b, [1,2,3])
```

```elixir
# https://hexdocs.pm/elixir/1.13/Enum.html
Enum.reduce([1,2,3], 0, &(&1 + &2))
```

Because of the way lists work, folds must first recurse over
the spine of the list from the beginning to the end. Left folds
traverse the spine in the same direction as right folds, but their
folding process is left associative and proceeds in the opposite
direction as that of foldr.

```haskell
type Foldable :: (* -> *) -> Constraint
class Foldable t where
  foldl :: (b -> a -> b) -> b -> t a -> b


rv :: [a] -> [a]
rv = foldl (\ xs y -> y:xs) [] -- strangely x is an array ([a])

rvScan :: [a] -> [[a]]
rvScan = scanl (\ xs y -> y:xs) []

flip :: (a -> b -> c) -> b -> a -> c -- flip the input parameters of a function
```

Scans are similar to folds but return a list of all the intermediate stages of
the fold.

## Custom Datatype

Refer to [Typeclass](#Typeclass)

```haskell
data Month = Jan | Feb | Mar | Apr | May | Jun | Jul | Aug | Sept | Oct | Nov | Dec
--    [1]    [2]
data [] a = [ ] | a : [a]
--   [3]    [4]    [5]
```

1. `[1]` is a Type Constructor with no arguement.
2. `[2]` Data constructor. In this case, a data constructor that takes no
arguments and so is called a *nullary* constructor.
3. `[3]` Type constructor with an argument. An empty list has
to be applied to an argument in order to become a list of something.
4. `[4]` Data constructor for the empty list.
5. `[5]` Data constructor that takes two arguments: an `a` and also a `[a]`.

Type constructors are used only at the type level, in type signatures and
typeclass declarations and instances. Types are static and resolve at compile
time.

Type and data constructors that take no arguments are constants. They can only
store a fixed type and amount of data.

Types are static and resolve at compile time.  Types are known before runtime,
whether through explicit declaration or type inference, and that’s what makes
them static types. Information about types does not persist through to runtime.
Data are what we’re working with at runtime.

### Arity

*Arity* refers to the number of arguments a function or constructor
takes.

A type can be thought of as an enumeration of constructors that have zero or
more arguments.

Data constructors that take one argument are called *unary*. data constructors
that take more than one argument are called *products*.

### Algebraic Datatype

Algebraic datatypes in Haskell are algebraic because we can
describe the patterns of argument structures using two basic
operations: sum and product.

Datatypes that only contain a unary constructor always have
the same *cardinality* as the type they contain.

*Cardinality* means capacity here. The number of possible values.
`Int8` has `2^8 = 256` possible values and `Bool` has `2` possible values.

For cardinality, this means unary constructors are the identity function.

A `newtype` cannot be a product type, sum type, or contain nullary constructors,
but it has a few advantages over a vanilla data declaration. One is that it has
no runtime overhead, as it reuses the representation of the type it contains.

One key contrast between a newtype and a type alias is that you can define
*typeclass instances* for `newtypes` that differ from the instances for their
underlying type.

### Product Type and Sum Type

In type theory, a product type is a type made of a set of types compounded over
each other. In Haskell we represent products using tuples or data constructors
with more than one argument.

```haskell
data BigSmall = Big Bool | Small Bool
```

The cardinality of `BigSmall` is

$$ 1\times 2+ 1\times 2 = 4$$

```haskell
import Data.Int
data NumberOrBool = Numba Int8 | BoolyBool Bool
```

The cardinality of `NumberOrBool` is

$$256+2$$

A product type’s cardinality is the product of the cardinalities of its
inhabitants.  Arithmetically, products are the result of multiplication. Where a
sum type was expressing or, a product type expresses and.

### Record

Hashmap-like data structures.

```haskell
data Person =
Person  { name :: String, 
          age  :: Int }
deriving (Eq, Show)
```

Accessing the fields of a record by `key`, like clojure `keyword`.

### Kinds

*Kinds* are the types of type constructors, primarily encoding the
number of arguments they take.

The kind `* -> *` is waiting for a single `*` before it is fully applied. The
kind `* -> * -> *` must be applied twice before it will be a real type. This is
known as a higher-kinded type. Lists, for example, are higher-kinded datatypes
in Haskell.

https://wiki.haskell.org/Kind
https://stackoverflow.com/questions/27095011/what-exactly-is-the-kind-in-haskell

> "kinds" are types for "types"

## Signaling adversity

- Maybe
- Either

### Either

```haskell
data Either a b = Left a | Right b
```

- Either is just like Option
- Right is just like `Some` (`Just`)
- Left is just like `None` (`Nothing`), except you can include content with it to describe the problem

### Kinds What?

`:kind` or `:k` in the REPL.

kind * represents a concrete type.

Data constructors are functions. As it happens, they behave like Haskell
functions in that they are curried as well.

## Algebra

### Monoid

幺半群, 又稱為單群、亞群、具幺半群或**四分之三群**.

- https://en.m.wikibooks.org/wiki/Haskell/Understanding_monads
- https://fsharpforfunandprofit.com/posts/monoids-without-tears/
- https://medium.com/@lettier/your-easy-guide-to-monads-applicatives-functors-862048d61610

Here's the rules.

- Rule 1 (Closure): The result of combining two things is always another one of the things.
- Rule 2 (Associativity): When combining more than two things, which pairwise
combination you do first doesn’t matter.
- Rule 3 (Identity element): There is a special thing called “zero” such that
when you combine any thing with “zero” you get the original thing back.

| Things           | Operation       | Closed?          | Associative?    | Identity?       | Classification |
|------------------|-----------------|------------------|-----------------|-----------------|----------------|
| Int32            | Addition        | Yes              | Yes             | 0               | Monoid         |
| Int32            | Multiplication  | Yes              | Yes             | 1               | Monoid         |
| Int32            | Subtraction     | Yes              | No              | 0               | Other          |
| Int32            | Max             | Yes              | Yes             | Int32.MinValue  | Monoid         |
| Int32            | Equality        | No               |                 |                 | Other          |
| Int32            | Less than       | No               |                 |                 | Other          |
| Float            | Multiplication  | Yes              | No (See note 1) | 1               | Other          |
| Float            | Division        | Yes (See note 2) | No              | 1               | Other          |
| Positive Numbers | Addition        | Yes              | Yes             | No identity     | Semigroup      |
| Positive Numbers | Multiplication  | Yes              | Yes             | 1               | Monoid         |
| Boolean          | AND             | Yes              | Yes             | true            | Monoid         |
| Boolean          | OR              | Yes              | Yes             | false           | Monoid         |
| String           | Concatenation   | Yes              | Yes             | Empty string "" | Monoid         |
| String           | Equality        | No               |                 |                 | Other          |
| String           | "subtractChars" | Yes              | No              | Empty string "" | Other          |
| List             | Concatenation   | Yes              | Yes             | Empty list []   | Monoid         |
| List             | Intersection    | Yes              | Yes             | No identity     | Semigroup      |

1. Floats are not associative. Replace ‘float’ with ‘real number’ to get associativity.
2. Mathematical real numbers are not closed under division, because you cannot
divide by zero and get another real number. However, with IEEE floating point
numbers you can divide by zero and get a valid value. So floats are indeed
closed under division!

```txt
A monoid is a binary associative operation with an identity.
   [1]         [2]       [3]        [4]              [5]
```

1. The thing we’re talking about — monoids. That’ll end up being the name of our
typeclass.
2. Binary, i.e., two. So, there will be two of something.
3. Associative — this is a property or law that must be satisfied. You’ve seen
associativity with addition and multiplication. We’ll explain it more in a
moment.
4. Operation — so called because in mathematics, it’s usually used as an *infix*
operator. You can read this interchangeably as “function.” Note that given the
mention of “binary” earlier, we know that this is a *function of two arguments*.
5. Identity is one of those words in mathematics that pops up a lot. In this
context, we can take this to mean there’ll be some value which, when combined
with any other value, will always return that other value.

For lists, we have a binary operator, `(++)`, that joins two lists together. We
can also use a function, `mappend`, from the Monoid typeclass to do the same thing.

```haskell
type Monoid :: * -> Constraint
class Semigroup a => Monoid a where
  mempty :: a
  mappend :: a -> a -> a
  mconcat :: [a] -> a
-- mconcat = foldr mappend mempty
  {-# MINIMAL mempty #-}
```

```haskell
import Data.Monoid
mappend (Sum 1) (Sum 5) -- Sum {getSum = 6}
mappend (Product 5) (Product 5) -- Product {getProduct = 25}
-- default operation for lists is concat
mappend [1..5] [1..3] -- [1,2,3,4,5,1,2,3]
```

Integers form a monoid under *summation and multiplication*. We can similarly say
that lists form a monoid under *concatenation*.

We’re going to be using the infix operator `<>` for `mappend`

```haskell
type Semigroup :: * -> Constraint
class Semigroup a where
  (<>) :: a -> a -> a

type Monoid :: * -> Constraint
class Semigroup a => Monoid a where
  mappend :: a -> a -> a
```

Because monoids are common and they’re a nice abstraction to work with when you
have multiple monoidal things running around in a project.

Algebras are defined by their `laws` and are useful principally
`for` their laws. Laws make up what algebras are.

```haskell
-- left identity
mappend mempty x = x
-- right identity
mappend x mempty = x
-- associativity
mappend x (mappend y z) =
mappend (mappend x y) z

mconcat :: [a] -> a
mconcat = foldr mappend mempty
```

$$1 + 0 = 1$$
$$0 + 1 = 1$$
$$(1 + 2) + 3 = 1 + (2 + 3)$$

```haskell
-- All equals And (&) in Boolean Algebras
All True <> All True  -- All {getAll = True}
All True <> All False -- All {getAll = False}
-- Any equals Or (|) in Boolean Algebras
Any False <> Any False -- Any {getAny = False}
Any False <> Any True  -- Any {getAny = True}
```

The Maybe type has more than two possible Monoids.  They are like boolean
disjunction, but with explicit preference for the leftmost or rightmost success
in a series of Maybe values.

With Maybe, however, you need to make a decision as to which Just value you’ll
return if there are multiple successes.

```haskell
First (Just 1) <> First (Just 2) -- First {getFirst = Just 1}
Last (Just 1) <> Last (Just 2)   -- Last {getLast = Just 2}

First (Nothing) <> First (Just 2) -- First {getFirst = Just 2}
Last (Nothing) <> Last (Just 2)   -- Last {getLast = Just 2}

-- returns Nothing if both Nothing
```

### Write a monoid

You have to write a `Semigroup` and `Monoid` instance for your own type.

```haskell
data Optional a = Nada | Only a deriving (Eq, Show)

-- https://stackoverflow.com/questions/52237895/could-not-deduce-semigroup-optional-a-arising-from-the-superclasses-of-an-in
instance Semigroup a => Semigroup (Optional a) where
  (<>) (Only x)  Nada      = Only x
  (<>) Nada      (Only y)  = Only y
  (<>) (Only x)  (Only y)  = Only (x <> y)
  (<>) Nada      Nada      = Nada

instance Monoid a => Monoid (Optional a) where
  mempty = Nada
```

In other words, you should define `<>` in a Semigroup instance using the
definition you currently have for `mappend`, and define `mempty` in a Monoid
instance. You can just ignore `mappend` since it’s only around for compatibility
reasons now, and its default implementation is `mappend = (<>)`.

If you’re wondering what’s weaker than *Semigroup*, the usual next step is
removing the associativity requirement, giving you a *magma*. It’s not likely to
come up in day to day Haskell, but you can sound cool at programming conferences
for knowing what’s weaker than a semigroup so pocket that one for the pub.

## Functor

Each of these algebras (Semigroup, Monoid, Applicative and Monad) is more
powerful than the last, but the general concept here will remain the same: we
abstract out a common pattern, make certain it follows some laws, give it an
awesome name, and wonder how we ever lived without it.

Functor is **mappable** thingy/object.

List is a functor.

```haskell
-- https://hackage.haskell.org/package/base-4.16.0.0/docs/Data-Functor.html
import Data.Functor.Constant
import Data.Functor.Identity 
-- Functor f =>
fmap :: (a -> b) -> fa -> fb
:: (a -> b) -> [] a -> [] b               -- List
:: (a -> b) -> Maybe a -> Maybe b         -- Maybe
:: (a -> b) -> Either e a -> Either e b   -- Either. e is some type. Can only apply to the right element.
:: (a -> b) -> (e, a) -> (e, b)           -- Tuple.  e is some type. Can only apply to the later element.
-- https://stackoverflow.com/questions/58560222/what-is-a-constant-functor
-- https://hackage.haskell.org/package/base-4.16.0.0/docs/Data-Functor-Identity.html
:: (a -> b) -> Identity a -> Identity b       -- Identity. No idea why not use `a` directly.
:: (a -> b) -> Constant e a -> Constant e b   -- Constant. Bascally it does nothing when be mapped.

instance Functor (Constant a) where
    fmap _ (Constant x) = Constant x
```

Why it skips the first value in the tuple? It has to do with the
kindedness of tuples and the kindedness of the *f* in Functor.

A functor is a way to apply a function over or around some structure that we
don’t want to alter. That is, we want to apply the function to the value that is
“inside” some structure and leave the structure alone.

```haskell
class Functor f where
  fmap :: (a -> b) -> f a -> f b
```

`:set -XTypeApplications` is a magic in GHCi.

`<$>` is the infix alias for fmap. For some reason I want you to see [Kinds](#kinds)

```haskell
(<$>) :: Functor f => (a -> b) -> f a -> f b
($) :: (a -> b) -> a -> b
```

What is `$` again? It is used to evaluate the value of type `a` first, and then
apply the function `(a -> b)` to it.

### Laws

1. Identity
2. Composition
3. Structure Preservation

```haskell
fmap id == id
fmap (f . g) == fmap f . fmap g

fmap :: Functor f => (a -> b) -> f a -> f b
-- the structure after fmapped is preserved (functor still applies)
```

> But what if you do want a function that can change the value and the structure?
We’ve got wonderful news for you: that exists! It’s a plain old function. Write
one. Write many!

We will talk about a sort of opposite, where you can transform the structure but
leave the type argument alone.

### Functor of function

```haskell
ghci> :t fmap (+1) negate
fmap (+1) negate :: Num b => b -> b

fmap (+1) negate 10 -- -9 = ((+1) . negate) 10 -- first negate then plus 1
```

https://stackoverflow.com/questions/48211978/haskell-function-composition-and-fmap-f

```haskell
-- `(->) r` (i.e. functions)
instance Functor ((->) r) where
    fmap = (.)
```

$$ (f \circ g)(x) = f(g(x)) $$

first apply g.

### What happens with Tuple and Either

Their kind is `* -> * -> *`, so as our custom type.

```haskell
-- kind of Two' is * -> *
data Two' a = Two a a deriving (Eq, Show)

-- Kind of Two and Or is * -> * -> *
data Two a b = Two a b deriving (Eq, Show)
data Or a b = First a | Second b deriving (Eq, Show)

instance Functor Two' where
  fmap f (Two x y) = Two (f x) (f y)
```

So to fix the kind incompatibility for our `Two` and `Or` types,
we apply one of the arguments of each type constructor, giving
us kind `* -> *`

```haskell
instance Functor (Two a) where
  fmap f (Two x y) = Two $ (f x) (f y)

-- This won’t fly, because the *a* is part of the functorial structure (the *f*).

-- It's the only solution I guess.
instance Functor (Two a) where
  fmap f (Two x y) = Two x (f y)

instance Functor (Or a) where
  fmap _ (First  x)  = First x      -- don't care what f is so just ignore it
  fmap f (Second y)  = Second (f y)
```

### fmap stacked together

```haskell
replaceWithP = const 'p'

lms = [Just "Ave", Nothing, Just "woohoo"]
replaceWithP lms -- 'p'

fmap replaceWithP lms -- ['p', 'p', 'p']
(fmap . fmap) replaceWithP lms -- [Just 'p',Nothing,Just 'p']
(fmap . fmap . fmap) replaceWithP lms -- [Just "ppp",Nothing,Just "pppppp"]


fmap replaceWithP :: Functor f => f a -> f Char

(fmap . fmap) replaceWithP
  :: (Functor f1, Functor f2) => f1 (f2 a) -> f1 (f2 Char)

(fmap . fmap . fmap) replaceWithP
  :: (Functor f1, Functor f2, Functor f3) =>
     f1 (f2 (f3 a)) -> f1 (f2 (f3 Char))
-- when f1 = [], f2 = Maybe, f3 = []
[Maybe [Char]] -> [Char]
[Maybe [Char]] -> [Maybe Char]
[Maybe [Char]] -> [Maybe [Char]]
```

### Use fmap to handle Maybe

```haskell
-- No need to pattern matching
incMaybe :: Num a => Maybe a -> Maybe a
incMaybe = fmap (+1)
showMaybe :: Show a => Maybe a -> Maybe String
showMaybe = fmap show

-- change the signature to get a more general function
-- implementation still the same
liftedInc :: (Functor f, Num b) => f b -> f b
liftedShow :: (Functor f, Show a) => f a -> f String

-- them apply to Either as well
-- and you should see why Right should be right
```

### IO Functor

Can't do anything useful with IO without Monad.

### Nat replace the container

https://stackoverflow.com/questions/3071136/what-does-the-forall-keyword-in-haskell-ghc-do

Turn on `RankNTypes` (or `Rank2Types`)

```haskell
type Nat f g = forall a . f a -> g a
```

## Applicative

https://en.wikipedia.org/wiki/Applicative_functor

*Monoid* gives us a means of mashing two values of the same type together.
*Functor*, on the other hand, is for function application over some structure we
don’t want to have to think about.

The Applicative typeclass allows for function application lifted over structure
(like Functor). But with Applicative the function we’re applying is also
embedded in some structure.

```haskell
class Functor f => Applicative f where
  pure :: a -> f a
  (<*>) :: f (a -> b) -> f a -> f b
```

The pure function does a simple and very boring thing: it lifts something into
functorial (applicative) structure.

`<*>` This is an infix function called *apply* or sometimes *ap* or sometimes
[*tie fighter*](https://en.wikipedia.org/wiki/TIE_fighter) when we’re feeling
particularly zippy.

```haskell
-- fmap
(<$>) :: Functor f
  => (a -> b) -> f a -> f b

-- apply
(<*>) :: Applicative f
  => f (a -> b) -> f a -> f b
```

```haskell
fmap (+1) [1..3]     -- [2,3,4]
pure (+1) <*> [1..3] -- [2,3,4]
```

```haskell
-- Same function body but different signature
pure 1 :: [Int]          -- [1]
pure 1 :: Maybe Int      -- Just 1
pure 1 :: Either a Int   -- Right 1
pure 1 :: ([a], Int)     -- ([],1)
```

with Applicative, we have a Monoid for our structure and
function application for our values

```haskell
-- Monoid
mappend ::       f    ->  f  ->  f
($)     ::   (a -> b) -> a   ->  b
(<*>)   :: f (a -> b) -> f a -> f b
```

Consider this.

```haskell
-- like for
[(*2), (*3)] <*> [4, 5] -- [2 * 4, 2 * 5, 3 * 4, 3 * 5] = [8,10,12,15]
[(+2), (*5)] <*> [1, 2, 3, 4]
```

```clojure
(for [x [4 5]
      y [#(* 2 %1) #(* 3 %1)]]
  (y x))

(for [x [1 2 3 4]
      y [#(+ 2 %1) #(* 5 %1)]]
  (y x))
```

But what about maybe?

```haskell
Just (*2) <*> Just 2 -- Just 4
Nothing <*> Just 2 -- == Just 2 <*> Nothing == Nothing <*> Nothing = Nothing

-- Notice that for the `a` value, we didn’t apply any function,
-- but they have combined themselves as if by magic; that’s due
-- to the Monoid instance for the `a` values.
("Woo", (+1)) <*> (" Hoo!", 0) -- ("Woo Hoo!", 1)

((+1), (+1)) <*> (0, 0) -- error. first one (type a) must be a Monoid

instance (Monoid a, Monoid b) => Monoid (a,b) where
  mempty = (mempty, mempty)
  (a, b) <$> (a',b') = (a <$> a', b <$> b')

instance Monoid a => Applicative ((,) a) where
  pure x = (mempty, x)
  (u, f) <*> (v, x) = (u <$> v, f x)

instance Applicative Maybe where
  pure = Just
  Nothing <*> _        =  Nothing
  _       <*> Nothing  =  Nothing
  Just f  <*> Just a   =  Just (f a)
```

Use tuple constructor

The Cartesian product is the product of two sets that results in all the ordered pairs
(tuples) of the elements of those sets.

```haskell
(,) <$> [1, 2] <*> [3, 4] -- -->
[(,) 1, (,) 2] <*> [3, 4] -- --> (,) 1 = (1, _) which needs a parameter to complete a tuple
[(1, 3), (1, 4), (2, 3), (2, 4)]

-- when fmap and applicative together things are getting interesting
(+) <$> [1, 2] <*> [3, 5] -- --> [(+1), (+2)] <*> [3, 5] -- --> [4,7]
```

```clojure
(for [x [3 5]
      y (map #(partial + %1) [1 2])]
  (y x))
```

How can this useful except calculation

```haskell
import Control.Applicative

lookup :: Eq a => a -> [(a, b)] -> Maybe b -- like a map or record

lookup 3 [(3, "hello")] -- Just "hello"

added :: Maybe Integer
added = pure (+3) <*> lookup 3 (zip [1, 2, 3] [4, 5, 6])
added = (+3) <$> lookup 3 (zip [1, 2, 3] [4, 5, 6]) -- a better solution? Don't forget Maybe is a Functor and can be mapped

y :: Maybe Integer
z :: Maybe Integer
tupled :: Maybe (Integer, Integer)
tupled = (,) <$>  y <*> z  -- --> Just ((,) y') <*> Just z'
```

`liftA2` equals `Monoid f => function <$> f a <*> f b`

### Applicative laws

1. identity
2. composition. It is the law stating that the result of composing our functions
first and then applying them and the result of applying the functions first then
composing them
should be the same.
3. homomorphism. A homomorphism is a structure-preserving map between two
algebraic structures.
4. Interchange

```haskell
-- identity
-- v is in functor
pure id <*> v = v
-- composition
-- u and v are functions in functor. (pure is the functor) and w is value in functor.
pure (.) <*> u <*> v <*> w = u <*> (v <*> w)
-- homomorphism
-- f is a function. pure is act like a functor, kind of
pure f <*> pure x = pure (f x)
-- interchange
-- u is function in functor. (pure is the functor)
-- To the left of <*> must always be a function embedded in some structure.
u <*> pure y = pure ($ y) <*> u
```

### ZipList

write something such that

```haskell
ZipList [1, 2, 3] <> ZipList [4, 5, 6] = [1 <> 4 , 2 <> 5 , 3 <> 6]
```

## Monad

Monads are applicative functors, but they have something special about them that
makes them diﬀerent from and more powerful than either <*> or fmap alone.

```haskell
class Applicative m => Monad m where
  (>>=) :: m a -> (a -> m b) -> m b
  (>>) :: m a -> m b -> m b
  return :: a -> m a
```

```haskell
fmap (+1) [1..3]         -- equals
[1..3] >>= return . (+1)
```

This happens to be a law, not a convenience. Functor, Applicative, and Monad
instances over a given type should have the same core behavior.

`Functor -> Applicative -> Monad` is the class dependency order.

`return`: it’s just the same as `pure`. All it does is take a value and return it
inside your structure, whether that structure is a list or Just or IO.

The next operator, `>>`, Some people do refer to it as the sequencing operator,
which sequences two actions while *discarding* any resulting value of the first
action.

The `>>=` operator is called bind. `do` syntax is a special case of bind.

```haskell
<$> :: Functor f
  => (a -> b) -> f a -> f b

<*> :: Applicative f
  => f (a -> b) -> f a -> f b

>>= :: Monad f
  => f a -> (a -> f b) -> f b
```

```haskell
addOne :: Num a => a -> [a]
addOne x = x:[1]

nested :: [[Integer]]
nested = (<$>) addOne [1, 2, 3]

normalList :: [Integer]
normalList = concat nested

concat :: Foldable t => t [a] -> [a]
-- or
concat :: [[a]] -> [a]

(>>=) [1, 2, 3] (\x -> [x,1])
[1, 2, 3] >>= (\x -> x:[1])

-- join is a general version of concat
import Control.Monad (join)
join :: Monad m => m (m a) -> m a

bind :: Monad m => (a -> m b) -> m a -> m b
bind f x = join $ f <$> x
```

*Monad*, in a sense, is a generalization of concat!

A monad is not:

1. Impure. Monadic functions are pure functions.
2. An embedded language for imperative programming. While monads are often used
for sequencing actions in a way that looks like imperative programming, there
are commutative monads that do not order actions.
3. A value.
4. About strictness.

### IO Monad

https://stackoverflow.com/questions/2468320/whats-the-difference-between-io-string-and-normal-string-in-haskell

```haskell
getLine :: IO String
putStrLn :: String -> IO ()
putStrLn <$> getLine -- --> putStrLn go through getLine (which is a IO Functor containing a String) and then return IO Unit
-- Functor f =>
--      (   a   ->   b  ) -> f  a      -> f  b
-- fmap (String -> IO ()) -> IO String -> IO(IO ()) and not the other way around
putStrLn <$> getLine :: IO (IO ())
-- but it's IO(IO ()), which won't do anything
join $ putStrLn <$> getLine :: IO () -- that's something
-- but this pattern can be expressed by >>=
getLine >>= putStrLn  -- first getLine, then putStrLn
```

### Do notation

https://en.wikibooks.org/wiki/Haskell/do_notation

It just creates a closure?

```haskell
-- action1 :: f a
-- bind the value `a` of monad `f` to x1 (now x1 is a value without monad)
-- action4 :: a -> b -> c -> f d
do  x1 <- action1
    x2 <- action2
    x3 <- action3
    action4 x1 x2 x3

action1 >>= (\x1 -> action2 >>= (\x2 -> action3 >>= (\x3 -> action4 x1 x2 x3)))

action1 >>= (\x1 ->
 action2 >>= (\x2 ->
  action3 >>= (\x3 ->
   action4 x1 x2 x3))) -- you see the pattern

-- for >> then operator

putStr "Hello" >> putStr " " >> putStr "world!" >> putStr "\n"

do  putStr "Hello"
    putStr " "
    putStr "world!"
    putStr "\n" 
```

https://stackoverflow.com/questions/37136294/what-operator-does

> `>>` is a shortcut for when you don't care about the value. That is, `a >> b` is
equivalent to `a >>= \_ -> b` (assuming a sane (or default) definition of `>>` in
the given monad).

`>>` performs the monadic action on the left hand side but discards its result
and then performs the right hand side.

```haskell
twiceWhenEven :: [Integer] -> [Integer]
twiceWhenEven xs = do
  x <- xs
  if even x
    then [x*x, x*x]
    else [x*x]

-- The x <- xs line binds individual values out of the list input,
-- like a list comprehension, giving us an `a`.

>>= :: Monad f
  => f a -> (a -> f b) -> f b

twiceWhenEven' :: [Integer] -> [Integer]
twiceWhenEven' xs = xs >>= (\x -> (if even x then [x*x, x*x] else [x*x]))
```

```haskell
data Cow = Cow {
    name :: String,
    age :: Int,
    weight :: Int
  } deriving (Eq, Show)
noEmpty :: String -> Maybe String
noEmpty "" = Nothing
noEmpty str = Just str
noNegative :: Int -> Maybe Int
noNegative n 
  | n >= 0 = Just n
  | otherwise = Nothing

-- if Cow's name is Bess, must be under 500
weightCheck :: Cow -> Maybe Cow
weightCheck c =
  let w = weight c 
      n = name c
  in if n == "Bess" && w > 499 then Nothing else Just c

-- "avoid lambda" will be ugly here
{-# HLINT ignore mkSimpleCow "Avoid lambda" #-}
mkSimpleCow :: String -> Int -> Int -> Maybe Cow
mkSimpleCow name age weight = noEmpty name >>= (\n -> 
                                noNegative age >>= (\a ->
                                  noNegative weight >>= (\w ->
                                    weightCheck $ Cow n a w)))

mkSimpleCow' :: String -> Int -> Int -> Maybe Cow
mkSimpleCow' name age weight = do n <- noEmpty name
                                  a <- noNegative age
                                  w <- noNegative weight
                                  weightCheck $ Cow n a w
```

What about either?

```haskell
j
(>>=) ::Monad m 
  => m a 
  -> (a -> m b) 
  -> m b
-- `Either e` as a monad
(>>=) :: Either e a
  -> (a -> Either e b)
  -> Either e b
```

### Monad Laws

```haskell
return :: Applicative m => a -> m a
pure   :: Functor     f => a -> f a
(>>=) :: m a -> (a -> m b) -> m b
-- right identity
m >>= return = m
-- left identity
return x >>= f = f x
-- associativity
(m >>= f) >>= g = m >>= (\x -> f x >>= g)
```

### Kleisli composition

```haskell
import Control.Monad
mcomp :: Monad m => (b -> m c) -> (a -> m b) -> a -> m c
mcomp  f g x = join $ f <$> g x
-- Why not:
mcomp' f g x = f =<< g x

-- We have already have some similar functions:
-- what we need is just flip the arguments
     (>=>) :: Monad m => (a -> m b) -> (b -> m c) -> a -> m c
flip (>=>) :: Monad m => (b -> m c) -> (a -> m b) -> a -> m c
```

## Foldable

> Revenge of the monoids

```haskell
type Foldable :: (* -> *) -> Constraint
class Foldable t where
  Data.Foldable.fold :: Monoid m => t m -> m  -- this
  foldMap :: Monoid m => (a -> m) -> t a -> m -- this
  foldr :: (a -> b -> b) -> b -> t a -> b
  foldl :: (b -> a -> b) -> b -> t a -> b
  foldr1 :: (a -> a -> a) -> t a -> a
  foldl1 :: (a -> a -> a) -> t a -> a
  Data.Foldable.toList :: t a -> [a]
  null :: t a -> Bool
  length :: t a -> Int
  elem :: Eq a => a -> t a -> Bool
  maximum :: Ord a => t a -> a
  minimum :: Ord a => t a -> a
  sum :: Num a => t a -> a
  product :: Num a => t a -> a
  {-# MINIMAL foldMap | foldr #-}
```

## Traversable

Traversable allows you to transform elements inside the structure like a
functor, producing applicative effects along the way, and lift those potentially
multiple instances of applicative structure outside of the traversable
structure.

It is commonly described as a way to traverse a data structure, mapping a
function inside a structure while accumulating the applicative contexts along
the way.

```haskell
type Traversable :: (* -> *) -> Constraint
class (Functor t, Foldable t) => Traversable t where
  traverse :: Applicative f => (a -> f b) -> t a -> f (t b) -- this
  sequenceA :: Applicative f => t (f a) -> f (t a)          -- this
  mapM :: Monad m => (a -> m b) -> t a -> m (t b)
  sequence :: Monad m => t (m a) -> m (t a)
  {-# MINIMAL traverse | sequenceA #-}
```

## Manage Project

https://stackoverflow.com/questions/30913145/what-is-the-difference-between-cabal-and-stack

Haskell programs are organized into modules. Modules contain the datatypes, type
synonyms, typeclasses, typeclass instances, and values you’ve defined at the top
level.

The Haskell Cabal, or Common Architecture for Building Applications and
Libraries, is a package manager.

## Testing

No idea.

## Applied Structure

https://grishaev.me/en/no-monads/
https://fsharpforfunandprofit.com/rop/
