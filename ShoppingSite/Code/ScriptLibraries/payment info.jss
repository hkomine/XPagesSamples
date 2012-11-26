/*
 * クレジットカードによる支払情報をスコープ変数にセットする
 */
function setCreditCard(_cardType, _cardHolderName, _cardNumber, _cardExpiration) {
	var thisPaymanet = new Object();
	
	thisPaymanet.paymentType = "CreditCard";
	thisPaymanet.cardType = _cardType;
	thisPaymanet.cardHolderName = _cardHolderName;
	thisPaymanet.cardNumber = _cardNumber;
	thisPaymanet.cardExpiration = _cardExpiration;
	thisPaymanet.handlingCharge = 0;
	
	sessionScope.payment = thisPaymanet;
	
	dumpPayment()
}

/*
 * クレジットカードが検証済みかどうか
 */
function isCreditCardValidated() {
	var p = sessionScope.payment;
	if (null != p) {
		return (p.paymentType == "CreditCard");
	} else {
		return false;
	}
}

/*
 * 銀行振込による支払情報をスコープ変数にセットする
 */
function setBankTransfer() {
	var thisPaymanet = new Object();
	
	thisPaymanet.paymentType = "BankTransfer";
	thisPaymanet.cardType = "";
	thisPaymanet.cardHolderName = "";
	thisPaymanet.cardNumber = "";
	thisPaymanet.cardExpiration = "";
	thisPaymanet.handlingCharge = 0;
	
	sessionScope.payment = thisPaymanet;
	
	dumpPayment()
}

/*
 * 代金引換による支払情報をスコープ変数にセットする
 */
function setOnDelivery(_handlingCharge) {
	var thisPaymanet = new Object();
	
	thisPaymanet.paymentType = "OnDelivery";
	thisPaymanet.cardType = "";
	thisPaymanet.cardHolderName = "";
	thisPaymanet.cardNumber = "";
	thisPaymanet.cardExpiration = "";
	thisPaymanet.handlingCharge = _handlingCharge;
	
	sessionScope.payment = thisPaymanet;
	
	dumpPayment()
}

/*
 * 支払情報を消去する
 */
function clearPayment() {
	sessionScope.payment = null;
}

/*
 * 支払情報を取り出す
 */
function getPayment() {
	return sessionScope.payment;
}

/*
 * 支払情報をコンソールにダンプする
 */
function dumpPayment() {
	var p = sessionScope.payment;
	print("paymentType=" + p.paymentType);
	print("handlingCharge=" + p.handlingCharge);
	print("cardType=" + p.cardType);
	print("cardHolderName=" + p.cardHolderName);
	print("cardNumbe=" + p.cardNumber);
	print("cardExpiration=" + p.cardExpiration);
}
