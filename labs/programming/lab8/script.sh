echo
for file in `find src -name '*.java'`
do
 echo "// ${file}"
 cat $file
done
