package test;

import it.csi.iride2.policy.entity.Identita;

public class GenerateIDIride {

	public static void main(String[] args) {
		Identita id = new Identita();
		id.setNome("MARIO");
		id.setCognome("ROSSI");
		id.setCodFiscale("MRARSS70H17I138F");
		id.setIdProvider("SP");
		id.setLivelloAutenticazione(1);
		System.out.println(id);
	}

}
