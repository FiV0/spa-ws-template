# {project}

Executing the program can either be done via
```
clj -M -m scratch :arg1 :arg2
```
or by compiling a jar via
```
clj -T:build clean
clj -T:build jar
```
and executing it via
```
java -jar target/lib-0.1.4.jar :arg1 :arg2
```
## License
