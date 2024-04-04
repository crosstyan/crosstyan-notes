#include <iostream>
#include <typeinfo>
#include <assert.h>

int main(){
  const bool x = 0b1;
  const bool y = 0b0;
  std::cout << "x=" << x << " type=" << typeid(x).name() << " sizeof=" << sizeof(x) << std::endl;
  std::cout << "y=" << y << " type=" << typeid(y).name() << " sizeof=" << sizeof(y) << std::endl;
  const int binary = 0b0010'1010;
  const int decimal = 42;
  const int hexadecimal = 0x2A;
  assert(binary == decimal && binary == hexadecimal && decimal == hexadecimal);
  std::cout << "binary=" << binary << " type=" << typeid(binary).name() << " sizeof=" << sizeof(binary) << std::endl;
  std::cout << "decimal=" << decimal << " type=" << typeid(decimal).name() << " sizeof=" << sizeof(decimal) << std::endl;
  std::cout << "hexadecimal=" << hexadecimal << " type=" << typeid(hexadecimal).name() << " sizeof=" << sizeof(hexadecimal) << std::endl;
  return 0;
}
