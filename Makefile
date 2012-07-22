ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
  COLON=\;
else
  COLON=:
endif

SRCS=$(wildcard src/*.java)

profiler.jar profiler.jar.pack.gz: $(SRCS) manifest.txt NetLogo.jar scala-library-2.9.2.jar
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.5 -target 1.5 -classpath NetLogo.jar$(COLON)scala-library-2.9.2.jar -d classes $(SRCS)
	jar cmf manifest.txt profiler.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip profiler.jar.pack.gz profiler.jar

NetLogo.jar:
	curl -f -s -S 'http://ccl.northwestern.edu/devel/NetLogo-971ed928.jar' -o NetLogo.jar

scala-library-2.9.2.jar:
	curl -f -s -S 'http://search.maven.org/remotecontent?filepath=org/scala-lang/scala-library/2.9.2/scala-library-2.9.2.jar' -o scala-library-2.9.2.jar

profiler.zip: profiler.jar
	rm -rf profiler
	mkdir profiler
	cp -rp profiler.jar profiler.jar.pack.gz README.md Makefile src manifest.txt profiler
	zip -rv profiler.zip profiler
	rm -rf profiler
