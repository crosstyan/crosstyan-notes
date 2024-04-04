(let [binary 2r00101010 ;; clojure don't have number separator (clojure have no 0b prefix for binary number)
      decimal 42
      hexadecimal 16r2A] ;; or 0x2A 
      (assert (= binary decimal hexadecimal)))

8r42