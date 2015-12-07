all:
	javac DataMgmt.java

submission:
	zip phase3.zip *

clean:
	rm *.class *.zip
