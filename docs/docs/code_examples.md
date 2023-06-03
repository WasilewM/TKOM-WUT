# Przykłady kodu w zaprojektowanym języku

```
Int main() {
	doSomeMath(1).print()

    List[Int] myList = [Int];
    myList.add(1);
    myList.add(12);
    myList.add(123);
    myList.print();

	return 0;
}

Int doSomeMath(Int n) {
   if ((n < 0) || (n == 0) || (n == 1)) {
      return 11;
   }

   Int i = 2;
   Int result = 0;
   while (i < n) {
      result = result - i * (i + n - i);
      i = i + 3;
   }

   return result;
}
```

Oraz efekty wykonania:

```
11
IntListValueScene[1, 12, 123]

Process finished with exit code 0
```
