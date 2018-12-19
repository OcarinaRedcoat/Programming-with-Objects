#!/bin/bash

NUM=0
SUC=0
FAIL=0
javac -cp po-uilib.jar:. `find sth -name "*.java"`
for x in tests/*.in; do
    NUM=$[$NUM+1]
    if [ -e ${x%.in}.import ]; then
        java -cp :po-uilib.jar:. -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp sth.app.App;
    else
        java -cp po-uilib.jar:. -Din=$x -Dout=${x%.in}.outhyp sth.app.App;
    fi
    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        echo -en "\033[01;31mFAIL: $x. See file ${x%.in}.diff" "\n"
	FAIL=$[$FAIL+1];
    else
	echo -en "\033[01;32mSUCCESS" "\n"
        rm -f ${x%.in}.diff ${x%.in}.outhyp
	SUC=$[$SUC+1];
    fi
   
done

rm -f saved*

echo -en "\n""\033[01;32mSUCCESS:" $SUC "of " $NUM " test" "\n"
echo -en "\033[01;31mFAIL: " $FAIL "of " $NUM " test" "\n"
find . -name '*.class' -exec rm -f {} \;  
