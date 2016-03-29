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
	
	
	
	
	
//	public static final String DIR = "C:\\BWM\\Interface\\gen3\\";
	public static final String DIR = "D:\\tmp\\gen4\\";
	public static final String DIR_BIG = "D:\\tmp\\genbig\\";

	public static final String DIR_ACCOUNT = DIR_BIG + "account\\";
	public static final String DIR_SUBACCOUNT = DIR_BIG + "subaccount\\";
	public static final String DIR_OUTSTANDING = DIR_BIG + "outstanding\\";
	public static final String DIR_EXECUTION = DIR_BIG + "execution\\";
	

	public static final String DIR_ACC = DIR_BIG + "acc\\";
	public static final String DIR_SUBACC = DIR_BIG + "subacc\\";
	public static final String DIR_POS = DIR_BIG + "position\\";
	public static final String DIR_TX = DIR_BIG + "transaction\\";
	
//	public static final String DIR_OUTSTANDING = DIR + "subaccount\\";
	
//	public static final String DIR_CUS = DIR + "cus\\";
//	public static final String DIR_DEP_ACC = DIR + "dep\\dep-acc\\";
//	public static final String DIR_DEP_POS = DIR + "dep\\dep-pos\\";
//	public static final String DIR_DEP_TX = DIR + "dep\\dep-tx\\";
//	public static final String DIR_FUNDCODE = DIR + "fund-code\\";
//	public static final String DIR_UH = DIR + "unitholder\\uh\\";
//	public static final String DIR_UT_POS = DIR + "unitholder\\ut-pos\\";
//	public static final String DIR_UT_TX = DIR + "unitholder\\ut-tx\\";
//	public static final String DIR_BONDMASTER = DIR + "bond\\bond-master\\";
//	public static final String DIR_BOND_POS = DIR + "bond\\bond-pos\\";
//	public static final String DIR_BOND_TX = DIR + "bond\\bond-tx\\";
//	public static final String DIR_LIABILITY_POS = DIR + "liability-pos\\";
//	public static final String DIR_FX = DIR + "exchange-rate\\";
//	public static final String DIR_RM_BRANCH = DIR + "rm-branch\\";
//	public static final String DIR_BM = DIR + "bm\\";
//	public static final String DIR_RM = DIR + "rm\\";
//	public static final String DIR_RM_MAP_CIF = DIR + "rm-map-cif\\";
//	public static final String DIR_CIF_MAP_BRANCH = DIR + "cif-map-branch\\";

	
//	===  deposit  === //
	public static final String FILE_NAME_ACCOUNT_DEPOSIT = "CP_ACCOUNT-deposit";
	public static final String FILE_NAME_SUBACCOUNT_DEPOSIT = "CP_SUBACCOUNT-deposit";
	public static final String FILE_NAME_OUTSTANDING_DEPOSIT = "CP_OUTSTANDING-deposit";
	public static final String FILE_NAME_EXECUTION_DEPOSIT = "CP_EXECUTION-deposit";

	public static final String FILE_NAME_DEP_ACC = "CP_BANKACCOUNT";
	public static final String FILE_NAME_DEP_SUBACC = "CP_SUBBANKACCOUNT";
	public static final String FILE_NAME_DEP_POS = "CP_DEPOSITOUTSTANDING";
	public static final String FILE_NAME_DEP_TX = "CP_DEPOSITEXECUTION";

//	===  fixed  === //
	public static final String FILE_NAME_ACCOUNT_FIXED = "CP_ACCOUNT-fixed";
	public static final String FILE_NAME_SUBACCOUNT_FIXED = "CP_SUBACCOUNT-fixed";
	public static final String FILE_NAME_OUTSTANDING_FIXED = "CP_OUTSTANDING-fixed";
	public static final String FILE_NAME_EXECUTION_FIXED = "CP_EXECUTION-fixed";

	public static final String FILE_NAME_BOND_ACC = "CP_FIXEDINCOMEACCOUNT";
	public static final String FILE_NAME_BOND_SUBACC = "CP_SUBFIXEDINCOMEACCOUNT";
	public static final String FILE_NAME_BOND_POS = "CP_FIXEDINCOMEOUTSTANDING";
	public static final String FILE_NAME_BOND_TX = "CP_FIXEDINCOMEEXECUTION";

//	===  unittrust  === //
	public static final String FILE_NAME_ACCOUNT_UNITTRUST = "CP_ACCOUNT-unittrust";
	public static final String FILE_NAME_SUBACCOUNT_UNITTRUST = "CP_SUBACCOUNT-unittrust";
	public static final String FILE_NAME_OUTSTANDING_UNITTRUST = "CP_OUTSTANDING-unittrust";
	public static final String FILE_NAME_EXECUTION_UNITTRUST = "CP_EXECUTION-unittrust";

	public static final String FILE_NAME_UT_ACC = "CP_UNITTRUSTACCOUNT";
	public static final String FILE_NAME_UT_SUBACC = "CP_SUBUNITTRUSTACCOUNT";
	public static final String FILE_NAME_UT_POS = "CP_UNITTRUSTOUTSTANDING";
	public static final String FILE_NAME_UT_TX = "CP_UNITTRUSTEXECUTION";

//	===  Margin  === //
	public static final String FILE_NAME_ACCOUNT_MARGIN = "CP_ACCOUNT-MARGIN";
	public static final String FILE_NAME_SUBACCOUNT_MARGIN = "CP_SUBACCOUNT-MARGIN";
	public static final String FILE_NAME_OUTSTANDING_MARGIN = "CP_OUTSTANDING-MARGIN";
	public static final String FILE_NAME_EXECUTION_MARGIN = "CP_EXECUTION-MARGIN";

	public static final String FILE_NAME_MARGIN_ACC = "CP_MARGINACCOUNT";
	public static final String FILE_NAME_MARGIN_SUBACC = "CP_SUBMARGINACCOUNT";
	public static final String FILE_NAME_MARGIN_POS = "CP_MARGINOUTSTANDING";
//	public static final String FILE_NAME_MARGIN_TX = "CP_MARGINEXECUTION";
	
//	===  Liability  === //
	public static final String FILE_NAME_ACCOUNT_LIAB = "CP_ACCOUNT-LIAB";
	public static final String FILE_NAME_SUBACCOUNT_LIAB = "CP_SUBACCOUNT-LIAB";
	public static final String FILE_NAME_OUTSTANDING_LIAB = "CP_OUTSTANDING-LIAB";
	public static final String FILE_NAME_EXECUTION_LIAB = "CP_EXECUTION-LIAB";

	public static final String FILE_NAME_LIAB_ACC = "CP_CREDITLOANACCOUNT";
	public static final String FILE_NAME_LIAB_SUBACC = "CP_SUBCREDITLOANACCOUNT";
	public static final String FILE_NAME_LIAB_POS = "CP_CREDITLOANOUTSTANDING";
	public static final String FILE_NAME_LIAB_TX = "CP_LIABEXECUTION";

	
//	public static final String FILE_NAME_CUS = "customer";
//	public static final String FILE_NAME_DEP_ACC = "deposit-acc";
//	public static final String FILE_NAME_FUNDCODE = "fund-code";
//	public static final String FILE_NAME_UH = "unittrust";
//	public static final String FILE_NAME_BONDMASTER = "bond-master";
//	public static final String FILE_NAME_LIABILITY_POS = "liability-pos";
//	public static final String FILE_NAME_FX = "exchange-rate";
//	public static final String FILE_NAME_RM_BRANCH = "rmbranch";
//	public static final String FILE_NAME_BM = "bm";
//	public static final String FILE_NAME_RM = "rm";
//	public static final String FILE_NAME_RM_MAP_CIF = "rm-map-cif";
//	public static final String FILE_NAME_CIF_MAP_BRANCH = "cif-map-branch";
	


	public static final String DIR_MARGIN = DIR + "margin\\";
	
	public static final String FILE_NAME_EQ = "bwm_kss_eq_mar_acc_mly_";
	public static final String FILE_NAME_DERI = "bwm_kss_deri_mar_acc_mly_";

	public static final String FILE_NAME_EQ_POS = "bwm_kss_eq_mar_pos_mly_";
	public static final String FILE_NAME_DERI_POS = "bwm_kss_deri_mar_pos_mly_";
	
	
	public static final String DIR_NON_BAY_DEBENTURE = DIR + "Non-Bay-Debenture\\";
	
	public static final String FILE_NAME_NON_BAY_DEBENTURE_MASTER = "bwm_Deb_NONBAY_mas_wly_";
	public static final String FILE_NAME_NON_BAY_DEBENTURE_TX = "bwm_Deb_NONBAY_trans_wly_";
	
	
	public static final String DIR_AYCAP = DIR + "AYCap\\";
	public static final String DIR_AYCAL = DIR + "AYCal\\";
	
	public static final String FILE_NAME_AYCAP_MASTER = "bwm_Loan_AYCAP_mas_mly_";
	public static final String FILE_NAME_AYCAP_POS = "bwm_Loan_AYCAP_pos_mly_";
	public static final String FILE_NAME_AYCAL_MASTER = "bwm_Loan_AYCAL_mas_mly_";
	public static final String FILE_NAME_AYCAL_POS = "bwm_Loan_AYCAL_pos_mly_";

}
