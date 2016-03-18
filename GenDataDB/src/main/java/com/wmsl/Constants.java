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

	public static final String DIR_EXECUTION = DIR + "big\\execution\\";
	public static final String DIR_OUTSTANDING = DIR + "big\\outstanding\\";
	public static final String DIR_POS = DIR + "big\\position\\";
	public static final String DIR_TX = DIR + "big\\transaction\\";
	
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
	

	public static final String FILE_NAME_OUTSTANDING_DEPOSIT = "CP_OUTSTANDING-deposit";
	public static final String FILE_NAME_OUTSTANDING_FIXED = "CP_OUTSTANDING-fixed";
	public static final String FILE_NAME_OUTSTANDING_UNITTRUST = "CP_OUTSTANDING-unittrust";

	public static final String FILE_NAME_EXECUTION_DEPOSIT = "CP_EXECUTION-deposit";
	public static final String FILE_NAME_EXECUTION_FIXED = "CP_EXECUTION-fixed";
	public static final String FILE_NAME_EXECUTION_UNITTRUST = "CP_EXECUTION-unittrust";
	
//	public static final String FILE_NAME_CUS = "customer";
//	public static final String FILE_NAME_DEP_ACC = "deposit-acc";
	public static final String FILE_NAME_DEP_POS = "CP_DEPOSITOUTSTANDING";
	public static final String FILE_NAME_DEP_TX = "CP_DEPOSITEXECUTION";
//	public static final String FILE_NAME_FUNDCODE = "fund-code";
//	public static final String FILE_NAME_UH = "unittrust";
	public static final String FILE_NAME_UT_POS = "CP_UNITTRUSTOUTSTANDING-pos";
	public static final String FILE_NAME_UT_TX = "CP_UNITTRUSTEXECUTION-tx";
//	public static final String FILE_NAME_BONDMASTER = "bond-master";
	public static final String FILE_NAME_BOND_POS = "CP_FIXEDINCOMEOUTSTANDING-pos";
	public static final String FILE_NAME_BOND_TX = "CP_FIXEDINCOMEEXECUTION-tx";
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
	public static final String FILE_NAME_NON_BAY_DEBENTURE_TRANSACTION = "bwm_Deb_NONBAY_trans_wly_";
	
	
	public static final String DIR_AYCAP = DIR + "AYCap\\";
	public static final String DIR_AYCAL = DIR + "AYCal\\";
	
	public static final String FILE_NAME_AYCAP_MASTER = "bwm_Loan_AYCAP_mas_mly_";
	public static final String FILE_NAME_AYCAP_POS = "bwm_Loan_AYCAP_pos_mly_";
	public static final String FILE_NAME_AYCAL_MASTER = "bwm_Loan_AYCAL_mas_mly_";
	public static final String FILE_NAME_AYCAL_POS = "bwm_Loan_AYCAL_pos_mly_";

}
