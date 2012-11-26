function getDigitAt(digitIndex, amount) {
	if (null == amount) {
		return "";
	}

	var length = amount.length;
	if (digitIndex > length) {
		return "";
	} else {
		strIndex = length - digitIndex;
		return amount.charAt(strIndex);
	}
}