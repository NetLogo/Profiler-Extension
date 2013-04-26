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

profiler.jar: $(SRCS) manifest.txt NetLogoHeadless.jar scala-library-2.10.1.jar
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.7 -target 1.7 -classpath NetLogoHeadless.jar$(COLON)scala-library-2.10.1.jar -d classes $(SRCS)
	jar cmf manifest.txt profiler.jar -C classes .

NetLogoHeadless.jar:
	curl -f -s -S 'http://ccl.northwestern.edu/devel/NetLogoHeadless-de4980d4.jar' -o NetLogoHeadless.jar

scala-library-2.10.1.jar:
	curl -f -s -S 'http://search.maven.org/remotecontent?filepath=org/scala-lang/scala-library/2.10.1/scala-library-2.10.1.jar' -o scala-library-2.10.1.jar

profiler.zip: profiler.jar
	rm -rf profiler
	mkdir profiler
	cp -rp profiler.jar README.md Makefile src manifest.txt profiler
	zip -rv profiler.zip profiler
	rm -rf profiler
