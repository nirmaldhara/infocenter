/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infocenterreceipt.enums;

/**
 *
 * @author ndhara
 */
public enum Message {
    	DATA_PARSING_FAIL("Data Parsing Fail!!"),
		NOT_VALID_DATA("Not a valid data"),
        NO_DATA("No data found"),
        CAN_NOT_PRINT("Can not print the receipt"),
		SETTING_SAVE_SUCCESS("Setting details saved successfully"),
	;


	private final String origin;

	private Message(String origin) {
		this.origin = origin;
	}

	public String get() {
		return origin;
	}

}
