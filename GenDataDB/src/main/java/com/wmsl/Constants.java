package com.wmsl;

public class Constants {

	public static final String DEFAULT_LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String DEFAULT_PIPE = "|";
	public static final String ENCODING = "UTF-8";
	public static final String FILENAME_BIG_EXT = ".big";
	public static final String FILENAME_TXT_EXT = ".txt";
	public static final String PREFIX_EQ = "EQ_";
	public static final String PREFIX_DERI = "DERI_";
	public static final String PREFIX_NONBAY_DEB = "NONBAY_DEB_";
	public static final String PREFIX_NONBAY_DEB_TX = "NONBAY_DEB_TX_";
	public static final String PREFIX_AYCAP = "AYCAP_";
	public static final String PREFIX_AYCAL = "AYCAL_";
	
	public static final String SOURCE_SUMMARY_ACC = "KSS-Equity";
	public static final String SOURCE_SUMMARY_DERI = "KSS-Derivative";
	public static final String SOURCE_NONBAY_DEB = "NONBAY-DEBENTURE";
	public static final String SOURCE_AYCAP = "AYCAP";
	public static final String SOURCE_AYCAL = "AYCAL";	
	
	

//	public static final String PATH_DELI = "\\";
//	public static final String DIR = "D:\\tmp\\gen4\\";
//	public static final String DIR_BIG = "D:\\tmp\\genbig\\";
//	public static final String DIR_LOG = "D:\\tmp\\logs\\";

	public static final String PATH_DELI = "/";
	public static final String DIR = "/Volumes/Data/BWM/Interface/tmp/gen4/";
	public static final String DIR_BIG = "/Volumes/Data/BWM/Interface/tmp/genBig/";
	public static final String DIR_LOG = "/Volumes/Data/BWM/Interface/tmp/logs/";


//	public static final String DIR_ACCOUNT = "account" + PATH_DELI;
//	public static final String DIR_SUBACCOUNT = "subaccount" + PATH_DELI;
//	public static final String DIR_OUTSTANDING = "outstanding" + PATH_DELI;
//	public static final String DIR_EXECUTION = "execution" + PATH_DELI;
//	
//
//	public static final String DIR_ACC = "acc" + PATH_DELI;
//	public static final String DIR_SUBACC = "subacc" + PATH_DELI;
//	public static final String DIR_POS = "position" + PATH_DELI;
//	public static final String DIR_TX = "transaction" + PATH_DELI;
	

	public static final String DIR_ACCOUNT = "";
	public static final String DIR_SUBACCOUNT = "";
	public static final String DIR_OUTSTANDING = "";
	public static final String DIR_EXECUTION = "";
	

	public static final String DIR_ACC = "";
	public static final String DIR_SUBACC = "";
	public static final String DIR_POS = "";
	public static final String DIR_TX = "";

//	===  deposit  === //
	public static final String DIR_DEP = DIR_BIG + "deposit" + PATH_DELI;
	public static final String FILE_NAME_ACCOUNT_DEPOSIT = "CP_ACCOUNT-deposit";
	public static final String FILE_NAME_SUBACCOUNT_DEPOSIT = "CP_SUBACCOUNT-deposit";
	public static final String FILE_NAME_OUTSTANDING_DEPOSIT = "CP_OUTSTANDING-deposit";
	public static final String FILE_NAME_EXECUTION_DEPOSIT = "CP_EXECUTION-deposit";

	public static final String FILE_NAME_DEP_ACC = "CP_BANKACCOUNT";
	public static final String FILE_NAME_DEP_SUBACC = "CP_SUBBANKACCOUNT";
	public static final String FILE_NAME_DEP_POS = "CP_DEPOSITOUTSTANDING";
	public static final String FILE_NAME_DEP_TX = "CP_DEPOSITEXECUTION";

//	===  fixed  === //
	public static final String DIR_FIXED = DIR_BIG + "fixed" + PATH_DELI;
	public static final String FILE_NAME_ACCOUNT_FIXED = "CP_ACCOUNT-fixed";
	public static final String FILE_NAME_SUBACCOUNT_FIXED = "CP_SUBACCOUNT-fixed";
	public static final String FILE_NAME_OUTSTANDING_FIXED = "CP_OUTSTANDING-fixed";
	public static final String FILE_NAME_EXECUTION_FIXED = "CP_EXECUTION-fixed";

	public static final String FILE_NAME_BOND_ACC = "CP_FIXEDINCOMEACCOUNT";
	public static final String FILE_NAME_BOND_SUBACC = "CP_SUBFIXEDINCOMEACCOUNT";
	public static final String FILE_NAME_BOND_POS = "CP_FIXEDINCOMEOUTSTANDING";
	public static final String FILE_NAME_BOND_TX = "CP_FIXEDINCOMEEXECUTION";

//	===  unittrust  === //
	public static final String DIR_UNITTRUST = DIR_BIG + "unittrust" + PATH_DELI;
	public static final String FILE_NAME_ACCOUNT_UNITTRUST = "CP_ACCOUNT-unittrust";
	public static final String FILE_NAME_SUBACCOUNT_UNITTRUST = "CP_SUBACCOUNT-unittrust";
	public static final String FILE_NAME_OUTSTANDING_UNITTRUST = "CP_OUTSTANDING-unittrust";
	public static final String FILE_NAME_EXECUTION_UNITTRUST = "CP_EXECUTION-unittrust";

	public static final String FILE_NAME_UT_ACC = "CP_UNITTRUSTACCOUNT";
	public static final String FILE_NAME_UT_SUBACC = "CP_SUBUNITTRUSTACCOUNT";
	public static final String FILE_NAME_UT_POS = "CP_UNITTRUSTOUTSTANDING";
	public static final String FILE_NAME_UT_TX = "CP_UNITTRUSTEXECUTION";

//	===  Margin  === //
//	public static final String DIR_MARG = DIR_BIG + "margin" + PATH_DELI;
	public static final String DIR_MARGIN_SUM_ACC = DIR_BIG + "summary-account" + PATH_DELI;
	public static final String DIR_MARGIN_SUM_DERI = DIR_BIG + "summary-Derivative" + PATH_DELI;
	
	public static final String FILE_NAME_ACCOUNT_MARGIN = "CP_ACCOUNT-MARGIN";
	public static final String FILE_NAME_SUBACCOUNT_MARGIN = "CP_SUBACCOUNT-MARGIN";
	public static final String FILE_NAME_OUTSTANDING_MARGIN = "CP_OUTSTANDING-MARGIN";
	public static final String FILE_NAME_EXECUTION_MARGIN = "CP_EXECUTION-MARGIN";

	public static final String FILE_NAME_MARGIN_ACC = "CP_MARGINACCOUNT";
	public static final String FILE_NAME_MARGIN_SUBACC = "CP_SUBMARGINACCOUNT";
	public static final String FILE_NAME_MARGIN_POS = "CP_MARGINOUTSTANDING";
	public static final String FILE_NAME_MARGIN_TX = "CP_MARGINEXECUTION";

//	===  Liability  === //
//	public static final String DIR_LIAB = DIR_BIG + "liability" + PATH_DELI;
	public static final String DIR_LIAB_AYCAP = DIR_BIG + "aycap" + PATH_DELI;
	public static final String DIR_LIAB_AYCAL = DIR_BIG + "aycal" + PATH_DELI;
	public static final String FILE_NAME_ACCOUNT_LIAB = "CP_ACCOUNT-LIAB";
	public static final String FILE_NAME_SUBACCOUNT_LIAB = "CP_SUBACCOUNT-LIAB";
	public static final String FILE_NAME_OUTSTANDING_LIAB = "CP_OUTSTANDING-LIAB";
	public static final String FILE_NAME_EXECUTION_LIAB = "CP_EXECUTION-LIAB";

	public static final String FILE_NAME_LIAB_ACC = "CP_CREDITLOANACCOUNT";
	public static final String FILE_NAME_LIAB_SUBACC = "CP_SUBCREDITLOANACCOUNT";
	public static final String FILE_NAME_LIAB_POS = "CP_CREDITLOANOUTSTANDING";
	public static final String FILE_NAME_LIAB_TX = "CP_CREDITLOANEXECUTION";

	
	
//	gen interface 1.2
	public static final String DIR_MARGIN = DIR + "margin" + PATH_DELI;
	
	public static final String FILE_NAME_EQ = "bwm_kss_eq_mar_acc_mly_";
	public static final String FILE_NAME_DERI = "bwm_kss_deri_mar_acc_mly_";

	public static final String FILE_NAME_EQ_POS = "bwm_kss_eq_mar_pos_mly_";
	public static final String FILE_NAME_DERI_POS = "bwm_kss_deri_mar_pos_mly_";
	
	
	public static final String DIR_NON_BAY_DEBENTURE = DIR + "Non-Bay-Debenture" + PATH_DELI;
	
	public static final String FILE_NAME_NON_BAY_DEBENTURE_MASTER = "bwm_Deb_NONBAY_mas_wly_";
	public static final String FILE_NAME_NON_BAY_DEBENTURE_TX = "bwm_Deb_NONBAY_trans_wly_";
	
	
	public static final String DIR_AYCAP = DIR + "AYCap" + PATH_DELI;
	public static final String DIR_AYCAL = DIR + "AYCal" + PATH_DELI;
	
	public static final String FILE_NAME_AYCAP_MASTER = "bwm_Loan_AYCAP_mas_mly_";
	public static final String FILE_NAME_AYCAP_POS = "bwm_Loan_AYCAP_pos_mly_";
	public static final String FILE_NAME_AYCAL_MASTER = "bwm_Loan_AYCAL_mas_mly_";
	public static final String FILE_NAME_AYCAL_POS = "bwm_Loan_AYCAL_pos_mly_";

}
