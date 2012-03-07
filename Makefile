NL_JAR_NAME=NetLogo.jar

ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifeq ($(origin SCALA_JAR), undefined)
  SCALA_JAR=$(NETLOGO)/lib/scala-library.jar
endif

ifeq (,$(findstring Cygwin,$(shell uname)))
  COLON=\;
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
  NETLOGO_JAR:=`cygpath -w "$(NETLOGO)"/"$(NL_JAR_NAME)"`
  SCALA_JAR:=`cygpath -w "$(NETLOGO)"/"$(SCALA_JAR)"`
else
  COLON=:
  NETLOGO_JAR:=$(NETLOGO)/$(NL_JAR_NAME)
endif

SRCS=$(wildcard src/*.java)

profiler.jar: $(SRCS) manifest.txt
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.5 -target 1.5 -classpath $(NETLOGO_JAR)$(COLON)$(SCALA_JAR) -d classes $(SRCS)
	jar cmf manifest.txt profiler.jar -C classes .

profiler.zip: profiler.jar
	rm -rf profiler
	mkdir profiler
	cp -rp profiler.jar README.md Makefile src manifest.txt profiler
	zip -rv profiler.zip profiler
	rm -rf profiler
