
#include <iostream>
#include "ListProblem.h"

int main() {
	ListProblem listProblem;
	listProblem.printList();
	listProblem.solve();
	std::cout << "After removing: ";
	listProblem.printList();
	return 0;
}