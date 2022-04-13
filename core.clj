(ns sicp.core)

(defn square [x] (* x x))

;; 1.2
(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5)))))
   (* 3 (- 6 2) (- 2 7)))


;; 1.3
;; just remove the smallest one
(defn sum-larger
  "A procedure that takes three numbers as arguments and returns the sum of the squares of the two larger numbers"
  [x y z]
  (let [xs [x y z]]
    (reduce + (map square  (remove #(= % (apply min xs)) xs)))))

(sum-larger 5 1 3)
(sum-larger 1 4 3)

;; 1.4
(defn a-plus-abs-b [a b]
  ((if (> b 0) + -) a b))

(a-plus-abs-b 1 -1)
(a-plus-abs-b 1 -100)
(a-plus-abs-b 1 100)

;; function can be returned as value

;; 1.5
;; No idea
;;    (test 0 (p))
;; => (if (= 0 0) 0 (p)))

;; (sqrt x) = the y such that y >= 0 and y^2 = x

;; Maybe one can use logic programming to solve this
(defn not-really-sqrt [x]
  (let [y :some-number]
    (and (>= y 0)
         (= (square y) x))))

;; Newton’s method of successive approximations
;; We can perform a simple manipulation to get a better guess
;; by averaging y with x/y

(defn abs [x] (cond
                (< x 0) (- x)
                :else   x))

;; clojure haven't got default parameter
;; so the 0.001
(defn sqrt-iter [guess x]
  (let [average (fn [x y] (/ (+ x y) 2))
        improve (fn [guess x] (average guess (/ x guess)))
        good-enough? (fn [guess x] (< (abs (- (square guess) x)) 0.001))]
    (if (good-enough? guess x) guess (recur (improve guess x) x))))

;; For instance, we can always guess that the square root of any number is 1
(defn sqrt [x] (sqrt-iter 1.0 x))

;; This might seem surprising, since we have not included in our language any iterative
;; (looping) constructs that direct the computer to do something over and over again.

;; use new-if
;; can only recur from tail position
(defn new-if [predicate then-clause else-clause]
  (cond predicate then-clause
        :else     else-clause))
;; if is a macro?

;; 1.7
;; An alternative strategy for implementing good-enough?
;; is to watch how "guess" changes from one iteration
;; to the next and to stop when the change is a very
;; small fraction of guess.
(defn sqrt-iter' [guess x last-change]
  (let [average (fn [x y] (/ (+ x y) 2))
        improve (fn [guess x] (average guess (/ x guess)))
        change  (abs (- (square guess) x))
        good-enough? (fn [change last-change] (< (abs (- change last-change)) 0.001))]
    (if (good-enough? change last-change) guess (recur (improve guess x) x change))))

(sqrt-iter' 1.0 9 0)
(sqrt-iter 1.0 9)

;; 1.8
(defn cube [x] (* x x x))

;; You can give a local name to the fn
;; you could also use letfn
;; Only change "improve" function
(defn cube-root-iter [guess x last-change]
  (let [improve (fn [y x] (/ (+ (/ x (square y)) (* 2 y)) 3))
        change  (abs (- (cube guess) x))
        good-enough? (fn [change last-change] (< (abs (- change last-change)) 0.001))]
    (if (good-enough? change last-change) guess (recur (improve guess x) x change))))

(defn factorial' [n]
  (cond
    (= n 1) 1
    :else   (* n (factorial (- n 1)))))

(defn factorial [n]
  (let [go (fn [n acc]
             (cond
               (= n 1) acc
               :else   (recur (- n 1) (* acc n))))]
    (go n 1)))

(factorial 6)

;; 1.10
;; Ackermann's function
;; See https://www.youtube.com/watch?v=i7sm9dzFtEI
;; I'm not interested in calculating these

(defn fib [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else   (+ (fib (- n 1))
               (fib (- n 2)))))

(defn fib' [n]
  (let [go (fn [a b count]
             (cond
               (= count 0) a
               :else       (recur b
                                  (+ a b)
                                  (- count 1))))]
    (go 0 1 n)))

;; A better to wrap your head
(defn fib'' [n]
  ;; the next iteration is f(n-2) f(n-1) when n = 2
  ;; maybe I will call n and n-max
  (let [go (fn [a b count n]
             (cond
               (< n 0)     nil
               (= n 0)     0
               (= n 1)     1
               (= count n) b
               :else       (recur b                         ; f(n+1-2) = f(n-1)
                                  (+ a b)                   ; f(n+1-1) = f(n) = f(n-1) + f(n-2)
                                  (inc count)
                                  n)))]
    ;; f(0) f(1) start from f(1)
    (go  0   1   1 n)))

(map fib (range 10))
(map fib' (range 10))
(map fib'' (range 10))

;; 1.11

(defn some-function [n]
  (cond
    (< n 3) n
    :else   (+ (some-function (- n 1)) (* 2 (some-function (- n 2))) (* 3 (some-function (- n 3))))))

(map some-function (range 10))

;; f(0) = 0
;; f(1) = 1
;; f(2) = 2
;; f(3) = f(2) + 2 * f(1) + 3 * f(0) = 4
;; f(4) = f(3) + 2 * f(2) + 3 * f(1)

;; https://www.youtube.com/watch?v=b1aAjlNnxT8&list=PLVFrD1dmDdvdvWFK8brOVNL7bKHpE-9w0&index=2
;; why?
(defn better-some-function [n]
  ;;         f(n-3)      f(n-2)    f(n-1) when n = 3 at first iteration
  (let [go (fn [a           b         c count]
             (cond
               (< count 0) count
               (= count 0) a
               :else  (recur b                              ; f(n-2) = f(n+1-3)
                             c                              ; f(n-1) = f(n+1-2)
                             (+ c (* 2 b) (* 3 a))          ; f(n)   = f(n+1-1)
                             (dec count))))]                ; count = count - 1
    ;; n is going up but count is going down
    ;; start from f(0) f(1)  f(2) and the start accumulator n is 2 when initialing
    (go            0     1    2   n)))

;; a version I think it's better for understanding
(defn better-some-function' [n]
  ;;         f(n-3)      f(n-2)    f(n-1) when n = 3 at first iteration
  (let [go (fn [a           b         c   count n]
             (cond
               (< n 3)     n
               (= count n) c
               :else  (recur b                              ; f(n-2) = f(n+1-3)
                             c                              ; f(n-1) = f(n+1-2)
                             (+ c (* 2 b) (* 3 a))          ; f(n)   = f(n+1-1)
                             (inc count)
                             n)))]                ; count = count - 1
    ;; n is going up but count is going down
    ;; start from f(0) f(1)  f(2) and the start accumulator n is 2 when initialing
    (go            0     1    2    2  n)))

(map better-some-function (range 10))
(map better-some-function' (range 10))
(some-function -1)
(better-some-function -1)

;; https://en.wikipedia.org/wiki/Pascal%27s_triangle

;; How can I come up with this?
;; See the little schemer
(defn calc-pt-middle [last]
  (cond
    (empty? (rest last)) '()
    :else         (cons (+ (first last) (first (rest last))) (calc-middle (rest last)))))

(calc-pt-middle '(1 1))
(calc-pt-middle '(1 2 1))
(calc-pt-middle '(1 3 3 1))
(calc-pt-middle '(1 4 6 4 1))

(defn pascal-triangle [n]
  (cond
    (<= n 0) '()
    (= n 1)  '(1)
    (= n 2)  '(1 1)
    :else   (concat '(1) (calc-pt-middle (pascal-triangle (dec n))) '(1))))

(map pascal-triangle (range 14))
(map #(reduce + %) (map pascal-triangle (range 14))) ;; 2^n

;; I don't prove anything
;; a useful function mentioned in
;; https://www.youtube.com/watch?v=b1aAjlNnxT8&list=PLVFrD1dmDdvdvWFK8brOVNL7bKHpE-9w0&index=2

(defn sliding [n col]
  (cond
    (empty? col) '()
    (< (count (take n col)) n) '()                          ; take less than need. Just give up.
    :else                 (cons (take n col) (sliding n (rest col)))))

(sliding 2 '(1 4 6 4 1))

(defn sliding-plus [n col]
  (map #(reduce + %) (sliding n col)))

(sliding-plus 2 '(1 4 6 4 1))

;; linear recursion
;; O(n) steps and O(n) space
(defn expt [b n]
  (cond
    (< n 0) (/ 1 (expt b (- n)))
    (= n 0) 1
    :else   (* b (expt b (dec n)))))

(defn expt' [b n]
  (let [go (fn [b n acc]
             (cond
               (= n 0) acc
               :else   (recur b (dec n) (* acc b))))]
    (if (>= n 0) (go b n 1) (/ 1 (go b (- n) 1)))))

;; O(n) steps and O(1) space
(expt' 2 8)
(expt' 2 -8)

;; We can also take advantage of successive squaring in computing exponential
;; b^n = (b^(n/2))^2
;; b^n = b \cdot b^(n-1)

(defn fast-expt [b n]
  (cond
    (= n 0) 1
    (even? n) (square (fast-expt b (/ n 2)))
    :else     (* b (fast-expt b (dec n)))))
;; O(log n) growth

(defn expt'' [b n]
  (reduce * (repeat n b)))

(expt'' 2 8)

;; https://codology.net/post/sicp-solution-exercise-1-16/
;; https://stackoverflow.com/questions/71021832/sicp-exercise-1-16-what-does-invariant-quantity-hint-mean
;; https://www.reddit.com/r/lisp/comments/7tecfh/understanding_the_invariant_quantity_technique_in/
;; using the observation that
;; (b^(n/2))^2 = (b^2)^(n/2)
(defn fast-expt' [b n]
  (let [go (fn [b n a]
             (cond
               (= n 0) a                                    ; why the return value is a?
               (even? n) (recur (square b) (/ n 2) a)
               :else     (recur b (dec n) (* a b))))]
    (go b n 1)))
; a*b^n is an invariant
;In general, the technique of defining an invariant quantity
;that remains unchanged from state to state is a powerful
;way to think about the design of iterative algorithms.

(fast-expt' 2 10)
(map #(fast-expt' 2 %) (range 14))

;In a similar way, one can perform
;integer multiplication by means of repeated addition.

(defn o* [a b]
  (cond
    (= b 0) 0
    :else   (+ a (o* a (dec b)))))

; Now suppose we include, together with "addition", operations
; "double", which doubles an integer, and "halve", which
; divides an (even) integer by 2.

;; b is even
;; a + b
;; a + (double (half b))
;; (half a) + (double b)
;; (double a) + (half b)

;; b is odd
;; a * b = (a + a) * (b - 1) ;; is this correct?
;; 5 * 3 = (5 + 5) * (3 - 1) = 20 ;; which is incorrect
;; 5 * 3 = 5 + (5 * (3 - 1)) = 15

;; https://codology.net/post/sicp-solution-exercise-1-17/
(defn o*' [a b]
  (let [double (fn [n] (* n 2))
        half   (fn [n] (/ n 2))
        go (fn go [a b]
             (cond
               (= b 1) a
               (even? b) (recur (double a) (half b))
               ;:else     (recur (+ a a)    (dec b))))]
               :else     (+ a (go a (dec b)))))]
(go a b)))

(o*' 5 4)

;; https://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
;; https://www.cut-the-knot.org/Curriculum/Algebra/PeasantMultiplication.shtml

;; https://clojuredocs.org/clojure.core/quot
;; I like the list version
(defn peasant [a b]
  (let [double (fn [n] (* n 2))
        half   (fn [n] (quot n 2))
        go (fn go [a b n]
             (cond
               (= a 0)   (reduce + n)
               (even? a) (recur (half a) (double b) n)
               :else     (recur (half a) (double b) (cons b n))))]
    (go a b '())))

;; https://codology.net/post/sicp-solution-exercise-1-18/
;; But it's the same
(defn peasant' [a b]
  (let [double (fn [n] (* n 2))
        half   (fn [n] (quot n 2))
        go (fn go [a b c]
             (cond
               (= a 0)   c
               (even? a) (recur (half a) (double b) c)
               :else     (recur (half a) (double b) (+ c b))))]
    (go a b 0)))

(peasant 18 85)
(peasant' 18 85)
(peasant 85 18)
(peasant 25 7)

;; sec 1.2.5 Greatest Common Divisors
;; sec 1.2.6 Testing for Primality
;; Skip. I don't care prime

(defn sum-range [a b]
  (cond
    (> a b) 0
    :else   (+ a (sum-range (inc a) b))))
(sum-range 1 5)
(reduce + [1 2 3 4 5])

;; sigma notation
