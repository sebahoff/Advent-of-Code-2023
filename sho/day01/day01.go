package main

import (
	"regexp"
	"strconv"
	"strings"
	"unicode"
)

const calibrationValues = "1abc2\npqr3stu8vwx\na1b2c3d4e5f\ntreb7uchet"
const calibrationValues2 = "two1nine\neightwothree\nabcone2threexyz\nxtwone3four\n4nineeightseven2\nzoneight234\n7pqrstsixteen"

var numArr = [...]string{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}

func main() {
	value := 0

	// check against calibration value
	lines := strings.Split(calibrationValues2, "\n")
	for _, line := range lines {
		// numbers := extractNumbersFromString(line) // golang regex isn't all that useful here
		num1 := getNumber(line, true)
		num2 := getNumber(line, false)

		concatted := string(rune(num1)) + "" + string(rune(num2))
		println(concatted)
		concNum, _ := strconv.Atoi(concatted)
		value += concNum
	}

	// file, _ := os.Open("./input.txt")
	// scanner := bufio.NewScanner(file)
	// for scanner.Scan() {
	// 	numbers := extractNumbersFromString(scanner.Text())
	// 	num1 := numbers[0]
	// 	num2 := numbers[len(numbers)-1]

	// 	concNum, _ := strconv.Atoi(string(num1) + "" + string(num2))
	// 	value += concNum
	// }

	println(value)
}

func extractNumbersFromString(s string) string {
	var re = regexp.MustCompile(`\D`)
	return re.ReplaceAllString(s, "")
}

func getNumber(s string, first bool) int {
	index := 0
	if !first {
		index = len(s) - 1
	}
	if unicode.IsDigit(rune(s[index])) {
		i, _ := strconv.Atoi(s)
		return i
	}
	for i, num := range numArr {
		if first && strings.HasPrefix(s, num) || !first && strings.HasSuffix(s, num) {
			return i
		}
	}
	return 0
}
