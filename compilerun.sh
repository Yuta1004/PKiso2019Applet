echo "Cleaning..."
rm *.class

echo "Compiling..."
javac Main.java

if [ $? -eq 0 ]; then
    echo "Running..."
    appletviewer Main.java
else
    echo "Compile Error"
fi
