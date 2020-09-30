package it.csi.gestionepazienti.gestionepazientiweb.util;

public class Constants {
	public final static String COMPONENT_NAME = "gestionepazientiweb";

	// project states
	public static final int PROJECT_STATUS_OPEN = 1;
	public static final int PROJECT_STATUS_IN_PROGRESS = 2;
	public static final int PROJECT_STATUS_WAIT = 3;
	public static final int PROJECT_STATUS_DONE = 4;
	public static final int PROJECT_STATUS_DELETED = 5;

	public static final int PROJECT_TYPE_PTE_MISURA = 1;
	public static final int PROJECT_TYPE_PTE = 2;
	public static final int PROJECT_TYPE_R_AND_D = 3;

	public static final int COLLECTION_STATUS_IN_PROGRESS = 1;
	public static final int COLLECTION_STATUS_DEFINED = 2;
	public static final int COLLECTION_STATUS_CONSOLIDATED = 3;
	public static final int COLLECTION_STATUS_IMPLEMENTED = 4;
	public static final int COLLECTION_STATUS_DELETED = 5;
	
	public static final int COLLECTION_TYPE_INGESTION_ID = 1;
	public static final int COLLECTION_TYPE_PREPARATION_ID = 2;
	public static final int COLLECTION_TYPE_CLASSIFICATION_ID = 3;
	
	public static final int COLLECTION_DISCLOSABLE_ID = 1;
	
	public static final int NEW_RELATION_OPERATION_TYPE_PREPARATION = 1;
	public static final int NEW_RELATION_OPERATION_TYPE_CLASSIFICATION_TREE = 2;
	public static final int NEW_RELATION_OPERATION_TYPE_CLASSIFICATION= 3;
	public static final int NEW_RELATION_OPERATION_TYPE_USAGE= 4;
	
	public static final int RELATION_TYPE_USAGE_ID = -1;
	public static final int RELATION_TYPE_PREPARATION_ID = 2;
	public static final int RELATION_TYPE_CLASSIFICATION_ID = 3;

	public static final String RELATION_NODE_TYPE_TARGET = "TARGET";
	
	public static final int USAGE_STATUS_IN_PROGRESS = 1;
	public static final int USAGE_STATUS_DEFINED = 2;
	public static final int USAGE_STATUS_IMPLEMENTED = 3;
	public static final int USAGE_STATUS_DELETED = 4;
	public static final int USAGE_TYPE_B2USER_ID = 1;
	public static final int USAGE_TYPE_B2B_ID = 2;
	
	
	public static final int DATASET_SYSTEM_INSTANCE_YUCCA = 1;
}
