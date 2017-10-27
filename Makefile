#The J variable is the jar compiler
J=jar
#The Javac compiler
JC=javac
#The flags to create a jar file
JFLAGS=cf
#The flags for compiling source
JCFLAGS=-d bin -Xlint:none

#The following variables are used for documentation generation
#see http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/javadoc.html
JD=javadoc
WTITLE="Duke's Klein Compiler API"
DTITLE="Klein Compiler API by The Dukes"
HEADER="<b>Duke Compiler</b>"
BOTTOM='<font size="-1"><a href="https://github.com/coswald/Dukes">Github Home</a><br/> Copyright &copy; 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek.<br/></font>'
SRCDIR="./src"

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
	@cp compiler-tools/kleinp .
	@cp compiler-tools/kleinv .

docs: sources
	@echo "Making docs..."
	@$(JD) -sourcepath $(SRCDIR) -d docs -use -splitIndex -windowtitle $(WTITLE) -doctitle $(DTITLE) -header $(HEADER) -bottom $(BOTTOM) -linkoffline https://docs.oracle.com/javase/8/docs/api/ https://docs.oracle.com/javase/8/docs/api/ @sources && make clean

clean:
	@echo "Cleaning up"
	@rm -R bin 2>/dev/null; rm sources 2>/dev/null
