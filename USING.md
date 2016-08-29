## Using the Profiler Extension

If you'd like your model to run faster, the profiler extension
may be useful to you. It includes primitives that measure how many
times the procedures in your model are called during a run and how
long each call takes. You can use this information to where to focus
your speedup efforts.

**Caution:**

The profiler extension is experimental. It is not yet well tested or
user friendly. Nonetheless, we think some users will find it useful.

### How to use

The profiler extension comes preinstalled. To use the extension in
your model, add a line to the top of your Code tab:

```NetLogo
extensions [profiler]
```

If your model already uses other extensions, then it already has an
`extensions` line in it, so just add `profiler` to the list.

For more information on using NetLogo extensions,
see the [Extensions Guide](http://ccl.northwestern.edu/netlogo/docs/extensions.html)

### Example

```NetLogo
setup                  ;; set up the model
profiler:start         ;; start profiling
repeat 20 [ go ]       ;; run something you want to measure
profiler:stop          ;; stop profiling
print profiler:report  ;; view the results
profiler:reset         ;; clear the data
```

Thanks to Roger Peppe for his contributions to the code.

