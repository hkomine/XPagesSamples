package com.company.shopping;

import java.util.Calendar;

public class CreditCardValidator {

	/*
	 * �N���W�b�g�J�[�h���؂̃��^�[���R�[�h
	 */
	public static int resultCodeCounter = 0;		// �L���ȃJ�[�h�̖߂�l
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
	 * ���̃v���O�����ŏ����ł���N���W�b�g�J�[�h�̎��
	 */
	private static final String cardType_BEST = "BEST";
	private static final String cardType_NICER = "Nicer";
	private static final String cardType_GOLD = "GOLD";

	/*
	 * �N���W�b�g�J�[�h�ԍ��̗L���Ȓ���
	 */
	private static final int  CARD_DIGIT_BEST = 10;
	private static final int  CARD_DIGIT_NICER = 10;
	private static final int  CARD_DIGIT_GOLD = 8;
	
	/**
	 * �N���W�b�g�J�[�h�o���f�[�^�[�̃C���X�^���X�擾
	 * 
	 * @return CreditCardValidator �̃C���X�^���X
	 */
	public static CreditCardValidator getInstance() {
		return new CreditCardValidator();
	}
	
	/**
	 * �N���W�b�g�J�[�h�o���f�[�^�[�̂̎��s
	 *
	 * @param cardType	�N���W�b�g�J�[�h�̎��
	 * @param customerName	�N���W�b�g�J�[�h���L�Җ�
	 * @param cardNumber	�N���W�b�g�J�[�h�ԍ�
	 * @param cardExpiration	�J�[�h�̗L�������̕����� mmyy
	 * @return	�J�[�h���؂̃��^�[���R�[�h
	 */
	public int validateCardInfo(String cardType, String customerName, String cardNumber, String cardExpiration) {
		int result;
		
		// ���̓f�[�^�̊m�F
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
		
		// �J�[�h���������ƔN�̐��l�ɕϊ�
		int expMonth = Integer.parseInt(cardExpiration.substring(0, 2));
		int expYear = Integer.parseInt(cardExpiration.substring(2, 4)) + 2000;
		
		// �J�[�h��Ђ��Ƃ̏����ɕ���
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
	 * BEST �N���W�b�g�J�[�h�̌���
	 * 
	 * @param customerName	�N���W�b�g�J�[�h���L�Җ�
	 * @param cardNumber	�N���W�b�g�J�[�h�ԍ�
	 * @param expMonth		�J�[�h�̗L�������i���j
	 * @param expYear		�J�[�h�̗L�������i�N�j
	 * @return	�J�[�h���؂̃��^�[���R�[�h
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
	 * Nicer �N���W�b�g�J�[�h�̌���
	 * 
	 * @param customerName	�N���W�b�g�J�[�h���L�Җ�
	 * @param cardNumber	�N���W�b�g�J�[�h�ԍ�
	 * @param expMonth		�J�[�h�̗L�������i���j
	 * @param expYear		�J�[�h�̗L�������i�N�j
	 * @return	�J�[�h���؂̃��^�[���R�[�h
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
	 * GOLD �N���W�b�g�J�[�h�̌���
	 * 
	 * @param customerName	�N���W�b�g�J�[�h���L�Җ�
	 * @param cardNumber	�N���W�b�g�J�[�h�ԍ�
	 * @param expMonth		�J�[�h�̗L�������i���j
	 * @param expYear		�J�[�h�̗L�������i�N�j
	 * @return	�J�[�h���؂̃��^�[���R�[�h
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
	 * ���ʂ̃N���W�b�g�J�[�h��񌟏�
	 * 
	 * @param customerName	�N���W�b�g�J�[�h���L�Җ�
	 * @param cardNumber	�N���W�b�g�J�[�h�ԍ�
	 * @param expMonth		�J�[�h�̗L�������i���j
	 * @param expYear		�J�[�h�̗L�������i�N�j
	 * @return	�L���ł���� true
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
	 * ���ʂ̃N���W�b�g�J�[�h�L����������
	 * 
	 * @param expMonth		�J�[�h�̗L�������i���j
	 * @param expYear		�J�[�h�̗L�������i�N�j
	 * @return	�L���ł���� true
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
	 * ���ʂ̃N���W�b�g�J�[�h�ԍ�����
	 * 
	 * @param text		�J�[�h�ԍ�
	 * @return	�L���ł���� true
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