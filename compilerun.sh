echo "Compiling..."
javac Main.java

if [ $1 -eq 0 ]; then
    echo "Running..."
    appletviewer Main.java
else
    echo "Compile Error"
fi
