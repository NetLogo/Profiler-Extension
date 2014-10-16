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

profiler.jar profiler.jar.pack.gz: $(SRCS) manifest.txt NetLogoHeadless.jar scala-library-2.10.4.jar
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.7 -target 1.7 -classpath NetLogoHeadless.jar$(COLON)scala-library-2.10.4.jar -d classes $(SRCS)
	jar cmf manifest.txt profiler.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip profiler.jar.pack.gz profiler.jar

NetLogoHeadless.jar:
	curl -f -s -S 'http://ccl.northwestern.edu/devel/6.0-M1/NetLogoHeadless.jar' -o NetLogoHeadless.jar

scala-library-2.10.4.jar:
	curl -f -s -S 'http://ccl.northwestern.edu/devel/scala-library-2.10.4.jar' -o scala-library-2.10.4.jar

profiler.zip: profiler.jar
	rm -rf profiler
	mkdir profiler
	cp -rp profiler.jar profiler.jar.pack.gz README.md Makefile src manifest.txt profiler
	zip -rv profiler.zip profiler
	rm -rf profiler
