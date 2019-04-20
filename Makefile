# Makefile for the Parsing Profiling Project

SHELL = /bin/bash

grammar_DIRECTORY	:=	grammars
CPP		:=	CPP14
GO		:=	Golang
JAVA	:=	${grammar_DIRECTORY}/JavaLexer.g4 ${grammar_DIRECTORY}/JavaParser.g4
# The Java grammar has two files, needing special rules for nearly every step

ANTLR	:=	java -jar /usr/local/lib/antlr-4.7.2-complete.jar

.PRECIOUS	:	${grammar_DIRECTORY}/%.class ${grammar_DIRECTORY}/%.java

default	:	antlr.class

# compile main file (a clean compilation could take a minute)
# the grammars do not implicitly build, run 'make grammar_all' first
antlr.class	:	antlr.java Rules.class ${CPP}Walker.class ${GO}Walker.class JavaWalker.class
	javac antlr.java

# run the main file with arguments
# for regular running the file with arguments it is advised to not use the make command
run	:	antlr.class
	-java antlr $(filter-out $@,$(MAKECMDGOALS))

JavaWalker.class	:	JavaWalker.java Rules.class ${grammar_DIRECTORY}/JavaLexer.class ${grammar_DIRECTORY}/JavaParser.class ${grammar_DIRECTORY}/JavaParserBaseListener.class ${grammar_DIRECTORY}/JavaParserListener.class
	javac $<

# implicit compilation rules for Walker.classes
%Walker.class	:	%Walker.java Rules.class ${grammar_DIRECTORY}/%Lexer.class ${grammar_DIRECTORY}/%Parser.class ${grammar_DIRECTORY}/%BaseListener.class ${grammar_DIRECTORY}/%Listener.class
	javac $<

Rules.class	: Rules.java
	javac $<

grammar_all	:	grammar_${CPP} grammar_${GO} grammar_java

# implicit compilation rules for ${grammar_DIRECTORY}
${grammar_DIRECTORY}/%.class	:	${grammar_DIRECTORY}/%.java
	javac $<

${grammar_DIRECTORY}/JavaLexer.java ${grammar_DIRECTORY}/JavaParser.java ${grammar_DIRECTORY}/JavaParserBaseListener.java ${grammar_DIRECTORY}/JavaParserListener.java	: ${JAVA}
	${ANTLR} -package ${grammar_DIRECTORY} ${JAVA}

# implicit grammar generation only rules
${grammar_DIRECTORY}/%Lexer.java ${grammar_DIRECTORY}/%Parser.java ${grammar_DIRECTORY}/%BaseListener.java ${grammar_DIRECTORY}/%Listener.java	: ${grammar_DIRECTORY}/%.g4
	-${ANTLR} -package ${grammar_DIRECTORY} $<

grammar_%	: ${grammar_DIRECTORY}/%.g4
	-${ANTLR} -package ${grammar_DIRECTORY} $<

# explicit grammar generation only rules
grammar_cpp	: ${grammar_DIRECTORY}/${CPP}.g4
	-${ANTLR} -package ${grammar_DIRECTORY} ${grammar_DIRECTORY}/${CPP}.g4

grammar_go	: ${grammar_DIRECTORY}/${GO}.g4
	-${ANTLR} -package ${grammar_DIRECTORY} ${grammar_DIRECTORY}/${GO}.g4

grammar_java: ${JAVA}
	${ANTLR} -package ${grammar_DIRECTORY} ${JAVA}

clean_lite	:
	-rm -f ${grammar_DIRECTORY}/*{Lexer,Parser,Listener}.java *.class

clean	:	clean_lite
	-rm -f ${grammar_DIRECTORY}/*.{tokens,interp,class}