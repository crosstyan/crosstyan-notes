# Vim Cheat Sheet

## Jump List

```txt
<C + o> - previous
<C + i> - next
:jumps  - open list command
```

## Change List

```txt
g; - previous
g, - next
:changes  - open list command
```

## Term Mode

```txt
term
```

## Marks

Don't confuse `;` with `'`, the previous one is used to move `vim-sneak` but the
later is jump to Mark line. They are very close!

[Using marks](https://vim.fandom.com/wiki/Using_marks)

```txt
m{a,z/A-Z} - set a mark
'{a,z/A-Z} - single quote: go to mark
`{a,z/A-Z} - backtick: go to exact position
[' - next line with lowercase mark
]' - previous line with lowercase mark
:marks - list marks
```

### Problem with vim-sneak

Marks and next item are not using the same key! (They are very close though)

```txt
 sneak#reset(key)                                        sneak#reset()
         Prevents Sneak from hijacking ; and , until the next invocation of
         Sneak. This is useful if you have remapped the Vim built-in f or
         t to another key and you still want to use ; and , for both Sneak
         and your custom "f" mapping.
             https://github.com/justinmk/vim-sneak/issues/114

         For example, to use "a" as your "f":

             nnoremap <expr> a sneak#reset('f')
             nnoremap <expr> A sneak#reset('F')
             xnoremap <expr> a sneak#reset('f')
             xnoremap <expr> A sneak#reset('F')
             onoremap <expr> a sneak#reset('f')
             onoremap <expr> A sneak#reset('F')

             Note: The <expr> modifier is required!
```

## Fold a function

just use `za`

```txt
    Vim folding commands
---------------------------------
zf#j creates a fold from the cursor down # lines.
zf/ string creates a fold from the cursor to string .
zj moves the cursor to the next fold.
zk moves the cursor to the previous fold.
za toggle a fold at the cursor.
zo opens a fold at the cursor.
zO opens all folds at the cursor.
zc closes a fold under cursor. 
zm increases the foldlevel by one.
zM closes all open folds.
zr decreases the foldlevel by one.
zR decreases the foldlevel to zero -- all folds will be open.
zd deletes the fold at the cursor.
zE deletes all folds.
[z move to start of open fold.
]z move to end of open fold.
```
