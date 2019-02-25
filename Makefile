# Makefile for the Parsing Profiling Project

SHELL = /bin/bash

grammar_DIRECTORY	:=	grammars
CPP		:=	CPP14
GO		:=	Golang
PYTHON	:=	Python3
# Java has two files, making a variable impratical

ANTLR	:=	java -jar /usr/local/lib/antlr-4.7.2-complete.jar

grammar_all : grammar_cpp grammar_go grammar_java grammar_py

# outputs errors describing conflicts with target language
#		this is normal and not an issue
grammar_cpp	: ${grammar_DIRECTORY}/${CPP}.g4
	-${ANTLR} -Dlanguage=Python3 ${grammar_DIRECTORY}/${CPP}.g4

# outputs errors describing conflicts with target language
#		this is normal and not an issue
grammar_go	: ${grammar_DIRECTORY}/${GO}.g4
	-${ANTLR} -Dlanguage=Python3 ${grammar_DIRECTORY}/${GO}.g4

# the Java parser and lexer is split between two files
#		this is normal and will not cause a problem
#		both are needed as arguments for antlr
grammar_java: ${grammar_DIRECTORY}/JavaLexer.g4 ${grammar_DIRECTORY}/JavaParser.g4
	${ANTLR} -Dlanguage=Python3 ${grammar_DIRECTORY}/Java{Lexer,Parser}.g4

grammar_py	: ${grammar_DIRECTORY}/${PYTHON}.g4
	${ANTLR} -Dlanguage=Python3 ${grammar_DIRECTORY}/${PYTHON}.g4

clean	:
	-rm -f ${grammar_DIRECTORY}/*.{py,tokens,interp}
