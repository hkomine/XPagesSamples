package com.company.shopping;

import java.util.Calendar;

public class CreditCardValidator {

	/*
	 * クレジットカード検証のリターンコード
	 */
	public static int resultCodeCounter = 0;		// 有効なカードの戻り値
	public static final int RESULTCODE_SUCCESS				= resultCodeCounter++;
	public static final int RESULTCODE_NO_cardType			= resultCodeCounter++;
	public static final int RESULTCODE_NO_CUSTOMERNAME		= resultCodeCounter++;
	public static final int RESULTCODE_NO_CARDNUMBER			= resultCodeCounter++;
	public static final int RESULTCODE_NO_cardExpiration		= resultCodeCounter++;
	public static final int RESULTCODE_UNKWONCARD				= resultCodeCounter++;
	public static final int RESULTCODE_INVALID_CARD_DATA		= resultCodeCounter++;
	public static final int RESULTCODE_EXPIRED				= resultCodeCounter++;
	public static final int RESULTCODE_NOTNUMBER				= resultCodeCounter++;
	public static final int RESULTCODE_BEST_NOT10DIGITS		= resultCodeCounter++;
	public static final int RESULTCODE_NICER_NOT10DIGITS		= resultCodeCounter++;
	public static final int RESULTCODE_GOLD_NOT8DIGITS		= resultCodeCounter++;
	public static final int RESULTCODE_BEST_UNACCEPTABLE		= resultCodeCounter++;
	public static final int RESULTCODE_NICER_UNACCEPTABLE		= resultCodeCounter++;
	public static final int RESULTCODE_GOLD_UNACCEPTABLE		= resultCodeCounter++;
	
	/*
	 * このプログラムで処理できるクレジットカードの種類
	 */
	private static final String cardType_BEST = "BEST";
	private static final String cardType_NICER = "Nicer";
	private static final String cardType_GOLD = "GOLD";

	/*
	 * クレジットカード番号の有効な長さ
	 */
	private static final int  CARD_DIGIT_BEST = 10;
	private static final int  CARD_DIGIT_NICER = 10;
	private static final int  CARD_DIGIT_GOLD = 8;
	
	/**
	 * クレジットカードバリデーターのインスタンス取得
	 * 
	 * @return CreditCardValidator のインスタンス
	 */
	public static CreditCardValidator getInstance() {
		return new CreditCardValidator();
	}
	
	/**
	 * クレジットカードバリデーターのの実行
	 *
	 * @param cardType	クレジットカードの種類
	 * @param customerName	クレジットカード所有者名
	 * @param cardNumber	クレジットカード番号
	 * @param cardExpiration	カードの有効期限の文字列 mmyy
	 * @return	カード検証のリターンコード
	 */
	public int validateCardInfo(String cardType, String customerName, String cardNumber, String cardExpiration) {
		int result;
		
		// 入力データの確認
		if ((null == cardType) || (0 == cardType.length())) {
			return RESULTCODE_NO_cardType;
		}
		if ((null == customerName) || (0 == customerName.length())) {
			return RESULTCODE_NO_CUSTOMERNAME;
		}
		if ((null == cardNumber) || (0 == cardNumber.length())) {
			return RESULTCODE_NO_CARDNUMBER;
		}
		if ((null == cardExpiration) || (4 != cardExpiration.length())) {
			return RESULTCODE_NO_cardExpiration;
		}
		
		// カード期限を月と年の数値に変換
		int expMonth = Integer.parseInt(cardExpiration.substring(0, 2));
		int expYear = Integer.parseInt(cardExpiration.substring(2, 4)) + 2000;
		
		// カード会社ごとの処理に分岐
		if (0 == cardType.compareTo(cardType_BEST)) {
			result = validateBESTCard(customerName, cardNumber, expMonth, expYear);
		} else if (0 == cardType.compareTo(cardType_NICER)) {
			result = validateNicerCard(customerName, cardNumber, expMonth, expYear);
		} else if (0 == cardType.compareTo(cardType_GOLD)) {
			result = validateGOLDCard(customerName, cardNumber, expMonth, expYear);
		} else {
			result = RESULTCODE_UNKWONCARD;
		}
		
		return result;
	}
	
	/**
	 * BEST クレジットカードの検証
	 * 
	 * @param customerName	クレジットカード所有者名
	 * @param cardNumber	クレジットカード番号
	 * @param expMonth		カードの有効期限（月）
	 * @param expYear		カードの有効期限（年）
	 * @return	カード検証のリターンコード
	 */
	private int validateBESTCard(String customerName, String cardNumber, int expMonth, int expYear) {
		
		if(! validateInput(customerName, cardNumber, expMonth, expYear)) {
			return RESULTCODE_INVALID_CARD_DATA;
		}

		if(! validateCardExpiration(expMonth, expYear)) {
			return RESULTCODE_EXPIRED;
		}
		
		if (!isNumber(cardNumber)) {
			return RESULTCODE_NOTNUMBER;
		}
		
		if (cardNumber.length() != CARD_DIGIT_BEST) {
			return RESULTCODE_BEST_NOT10DIGITS;
		}
		
		if (0 == cardNumber.compareTo("0000000000")) {
			return RESULTCODE_BEST_UNACCEPTABLE;
		}
		
		return RESULTCODE_SUCCESS;
	}
	
	/**
	 * Nicer クレジットカードの検証
	 * 
	 * @param customerName	クレジットカード所有者名
	 * @param cardNumber	クレジットカード番号
	 * @param expMonth		カードの有効期限（月）
	 * @param expYear		カードの有効期限（年）
	 * @return	カード検証のリターンコード
	 */
	private int validateNicerCard(String customerName, String cardNumber, int expMonth, int expYear) {

		if(! validateInput(customerName, cardNumber, expMonth, expYear)) {
			return RESULTCODE_INVALID_CARD_DATA;
		}
		
		if(! validateCardExpiration(expMonth, expYear)) {
			return RESULTCODE_EXPIRED;
		}
		
		if (!isNumber(cardNumber)) {
			return RESULTCODE_NOTNUMBER;
		}
		
		if (cardNumber.length() != CARD_DIGIT_NICER) {
			return RESULTCODE_NICER_NOT10DIGITS;
		}
		
		if (0 == cardNumber.compareTo("0000000000")) {
			return RESULTCODE_NICER_UNACCEPTABLE;
		}
		
		return RESULTCODE_SUCCESS;
	}
	
	/**
	 * GOLD クレジットカードの検証
	 * 
	 * @param customerName	クレジットカード所有者名
	 * @param cardNumber	クレジットカード番号
	 * @param expMonth		カードの有効期限（月）
	 * @param expYear		カードの有効期限（年）
	 * @return	カード検証のリターンコード
	 */
	private int validateGOLDCard(String customerName, String cardNumber, int expMonth, int expYear) {
		
		if(! validateInput(customerName, cardNumber, expMonth, expYear)) {
			return RESULTCODE_INVALID_CARD_DATA;
		}
		
		if(! validateCardExpiration(expMonth, expYear)) {
			return RESULTCODE_EXPIRED;
		}
		
		if (!isNumber(cardNumber)) {
			return RESULTCODE_NOTNUMBER;
		}
		
		if (cardNumber.length() != CARD_DIGIT_GOLD) {
			return RESULTCODE_GOLD_NOT8DIGITS;
		}
		
		if (0 == cardNumber.compareTo("00000000")) {
			return RESULTCODE_GOLD_UNACCEPTABLE;
		}
		
		return RESULTCODE_SUCCESS;
	}
	
	/**
	 * 共通のクレジットカード情報検証
	 * 
	 * @param customerName	クレジットカード所有者名
	 * @param cardNumber	クレジットカード番号
	 * @param expMonth		カードの有効期限（月）
	 * @param expYear		カードの有効期限（年）
	 * @return	有効であれば true
	 */
	private boolean validateInput(String customerName, String cardNumber, int expMonth, int expYear) {
		
		if ((null == customerName) || (0 == customerName.length())) {
			return false;
		}
		if ((null == cardNumber) || (0 == cardNumber.length())) {
			return false;
		}
		if ((expMonth < 1) || (expMonth > 12)) {
			return false;
		}
		if ((expYear < 2011) || (expYear > 2050)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 共通のクレジットカード有効期限検証
	 * 
	 * @param expMonth		カードの有効期限（月）
	 * @param expYear		カードの有効期限（年）
	 * @return	有効であれば true
	 */
	private boolean validateCardExpiration(int expMonth, int expYear) {
		Calendar now = Calendar.getInstance();
		int nowYear = now.get(Calendar.YEAR);
		int nowMonth = now.get(Calendar.MONTH);
		
		if (expYear > nowYear) {
			return true;
		} else if (expYear == nowYear) {
			if (expMonth > nowMonth) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 共通のクレジットカード番号検証
	 * 
	 * @param text		カード番号
	 * @return	有効であれば true
	 */
	private boolean isNumber(String text) {
		char[] chars = text.toCharArray();
		
		for (int i=0; i<chars.length; i++) {
			if (! Character.isDigit(chars[i])) {
				return false;
			}
		}
		
		return true;
	}

}