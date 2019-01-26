echo "Cleaning..."
rm *.class

echo "Compiling..."
javac i17027.java

if [ $? -eq 0 ]; then
    echo "Running..."
    appletviewer i17027.java
else
    echo "Compile Error"
fi
