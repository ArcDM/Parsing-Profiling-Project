package main

/**	This program demonstrates all of the control statements I know of.
  *	If you know of any other methods of printing, please add them.
  */
func funct() {
	return;
}

type struct_example struct {
    Var string
}

func main() {
	var index int = 0;

	if index == 0 {
		index++;
	}

	if index > 0 {
		index--;
	} else {
		index++;
	}

	if index > 0 {
		index--;
	} else if index < 0 {
		index++;
	} else {
		index = 5;
	}

	for index > 0 {
		index--;
	}

	for i := 10; i > 0; i-- {
		i--;
	}

	for i := 10; i > 0; i-- {
		if i > 5 {
			continue;
		}

		if i < 4 {
			break;
		}
	}

	goto lable;

	funct();

	lable:
 
	switch index {
		case 0:
			index = 5;
		case 1:
			fallthrough;
		case 2:
			index--;
		default:
			index++;
	}
}
