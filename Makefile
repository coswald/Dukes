
#The J variable is the jar compiler
J=jar
#The Javac compiler
JC=javac
#The flags to create a jar file
JFLAGS=cf
#The flags for compiling source
JCFLAGS=-d bin -Xlint:none 

all: jar clean
	@mv klein.jar compiler-tools/
	@echo "Moved Jar to compiler-tools..."
jar: bin
	@cd bin && $(J) $(JFLAGS) ../klein.jar * && cd ..
	@echo "Made Jar..."
bin: sources
	@mkdir bin && $(JC) $(JCFLAGS) @sources
	@echo "Compiling successful"
sources:
	@echo "Finding source files"
	@find . -iname *.java > sources

tools:
	@cp compiler-tools/kleins .
	@cp compiler-tools/kleinf .

clean:
	@echo "Cleaning up"
	@rm -R bin && rm sources
