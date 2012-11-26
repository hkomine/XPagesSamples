/*
 * クレジットカードバリデーターのの実行
 *
 * @param cardType	クレジットカードの種類
 * @param customerName	クレジットカード所有者名
 * @param cardNumber	クレジットカード番号
 * @param cardExpiration	カードの有効期限の文字列 mmyy
 * @return	カード検証のリターンコード
 */
function validateCardInfo(cardType, cardHolderName, cardNumber, cardExpiration) {
	var validator = com.company.shopping.CreditCardValidator.getInstance();
	var result = validator.validateCardInfo(cardType, cardHolderName, cardNumber, cardExpiration);
	
	return result;
}

/*
 * カード検証のリターンコードをメッセージ文字列に変換する
 * 
 * @param result	リターンコード
 * @return	リターンコードの内容を示すメッセージ
 */
function getReasonMessage(resultCode) {
	var message = "不明な結果です。サポートセンターにお問い合わせください。";
	switch (resultCode) {
		case com.company.shopping.CreditCardValidator.RESULTCODE_SUCCESS: 			message = "クレジットカードが認証されました。"; break
		case com.company.shopping.CreditCardValidator.RESULTCODE_NO_cardType:		message = 	"カード会社名が指定されていません。"; break;
		case com.company.shopping.CreditCardValidator.RESULTCODE_NO_CUSTOMERNAME:	message = "カードの所有社名が指定されていません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_NO_CARDNUMBER:		message = "カード番号が指定されていません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_NO_cardExpiration:	message = "カード有効期限が正しく指定されていません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_UNKWONCARD:		message = 	"このカード会社での支払いは出来ません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_INVALID_CARD_DATA:	message = "カードの入力内容が正しくありません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_EXPIRED:			message = "カードの有効期限が切れています。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_NOTNUMBER:			message = 	"カード番号に数字でない文字が含まれています。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_BEST_NOT10DIGITS:	message = "BEST カードでは 10桁のカード番号を入力してください。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_NICER_NOT10DIGITS: message = "Nicer カードでは 10桁のカード番号を入力してください。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_GOLD_NOT8DIGITS:	message = "GOLD カードでは 8桁のカード番号を入力してください。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_BEST_UNACCEPTABLE: message = "BEST カードでは、このカード番号はお受けできません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_NICER_UNACCEPTABLE: message = "Nicer カードでは、このカード番号はお受けできません。"; break;
        case com.company.shopping.CreditCardValidator.RESULTCODE_GOLD_UNACCEPTABLE: message = "GOLD カードでは、このカード番号はお受けできません。"; break;
	}
	return message;
}