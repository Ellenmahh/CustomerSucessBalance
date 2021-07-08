import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	static Map<String, String> idNivelCliente = new HashMap<String, String>();
	static Map<String, String> idNivelCss = new HashMap<String, String>();
	static Map<String, Integer> idNivel = new HashMap<String, Integer>();
	static List<String> clientesAdc = new ArrayList<>();

	public static void main(String[] args) {
		String css = JOptionPane.showInputDialog("Digite o id e nivel dos CSs disponiveis. ex.:1:50,4:100");
		String clientes = JOptionPane.showInputDialog("Digite o id e nivel dos clientes. ex.:1:20,2:30,3:40");
		String abstento = JOptionPane.showInputDialog("Digite o id dos CSs abstentos. ex:2,3");
		
//		  String css = "1:50,3:100";
//        String clientes = "1:20,2:30,3:35,4:55,5:60,6:80";
//        String abstento = "2";

		customerSuccessBalancing(css, clientes, abstento);
		
	}

	private static void customerSuccessBalancing(String css, String clientes, String abstento) {
		idNivelCss = validCustomerSucess(css, clientes);
		validCssAbstento(css, abstento);
		idNivelCliente = validClientes(clientes);
		
		Integer numCss = 0;
        Integer ultimoNvClient = 0;
        Map<Integer, Integer> atds = new HashMap<>();
		
        for(String keyCs : idNivelCss.keySet()){
            numCss = 0;
            ultimoNvClient++;
            for(String keyCli : idNivelCliente.keySet()){
                    if(Integer.parseInt(idNivelCss.get(keyCs))>= Integer.parseInt(idNivelCliente.get(keyCli)) 
                    		&& Integer.parseInt(idNivelCliente.get(keyCli)) >= ultimoNvClient){
                        numCss++;
                        ultimoNvClient = Integer.parseInt(idNivelCliente.get(keyCli));
                    }
                    
                    atds.put(Integer.parseInt(keyCs), numCss);
            }
        }
        
        getEmpate(atds);
        
        getMaxEntryInMapValue(atds);
        
	}

	private static void getEmpate(Map<Integer, Integer> atds) {
		List<Integer> value = new ArrayList<Integer>();
		Collection<Integer> v = atds.values();

		for(Integer x : v) {
			value.add(x);
		}

		int cont = 0 ;
		
		for (int i = 0; i < atds.size(); i++) {
			
			for(Integer vv : value) {
				String valueAtual = String.valueOf(value.get(i));
		
				if(valueAtual.contains(String.valueOf(vv))) {
					cont++;
				}
			}
		}
		
		if(cont > 1) {
			showMessageDialog("empate");
		}
		
	}

	private static Map<String, String>  validCustomerSucess(String css, String clientes) {
		String[] c = css.split(",");
		
		for (int i = 0; i < c.length; i++) {
			String[] x = c[i].split(":");
			
			String id = x[0];
			String nivel = x[1];
			
			//0 < n < 1.000
			if(c.length > 1000) {
				showMessageDialog("Limite de CSs ultrapassado, é permitido até 1000.");
			}
			//0 < id do cs < 1.000
			if(Integer.parseInt(id) < 0 || Integer.parseInt(id) > 1000) {
				showMessageDialog("Id "+ id +" inválido! O range dos ids deve ser entre 0 e 1000");
			}
			//0 < nível do cs < 10.000
			if(Integer.parseInt(nivel) < 0 || Integer.parseInt(nivel) > 10000) {
				showMessageDialog("Nível "+ nivel +" inválido! O range de níveis deve ser entre 0 e 10.000");
			}
			
			idNivelCss.put(id, nivel);
			
		}
		return idNivelCss;
	}
	
	private static Map<String, String> validClientes(String clientes) {
		String[] cc = clientes.split(",");

		String idCliente = "";
		String nivelCliente = "";
		
		for (int ii = 0; ii < cc.length; ii++) {
			String[] xx = cc[ii].split(":");
			
			idCliente = xx[0];
			nivelCliente = xx[1];
			
			//0 < m < 1.000.000
			if(cc.length > 100000) {
				showMessageDialog("Limite de Clientes ultrapassado, é permitido até 1.000.000.");
			}
			//0 < id do cliente < 1.000.000
			if(Integer.parseInt(idCliente) < 0 || Integer.parseInt(idCliente) > 100000) {
				showMessageDialog("Id "+ idCliente +" inválido! O range dos ids deve ser entre 0 e 1.000.000");
			}
			
			//0 < tamanho do cliente < 100.000
			if(Integer.parseInt(nivelCliente) < 0 || Integer.parseInt(nivelCliente) > 100000) {
				showMessageDialog("Nível "+ nivelCliente +" inválido! O range de níveis deve ser entre 0 e 100.000");
			}
			
			idNivelCliente.put(idCliente, nivelCliente);
			
		}
		
		return idNivelCliente;
	}
	
    public static <K, V extends Comparable<V> > Map.Entry<K, V> getMaxEntryInMapValue(Map<K, V> map) {
        Map.Entry<K, V> maxValue = null;
  
        for (Map.Entry<K, V> currentEntry : map.entrySet()) {
  
            if (maxValue == null || currentEntry.getValue().compareTo(maxValue.getValue()) > 0) {
  
                maxValue = currentEntry;
            }
        }
  
        showMessageDialog("O CustomerSucess que atenderá mais clientes é o ID: "+maxValue.getKey());
        
        return maxValue;
    }
	
	public static void validCssAbstento (String css, String cssAbstento) {
		String[] cs = css.split(",");
		int permitido = cs.length/2;
		
		String[] x = cssAbstento.split(",");
		
		for (int i = 0; i < x.length; i++) {
			if(x[i].length() > permitido) {
				showMessageDialog("A quantidade de CustomersSucess abstinentes não pode ultrapassar a metade do total de CSs disponíveis: " +Math.floor(permitido));
			}
		}
		
	}
	
	private static void showMessageDialog(String var) {
		JFrame frame = new JFrame("JOptionPane showMessageDialog ");
		JOptionPane.showMessageDialog(frame, var);
		System.exit(0);
	}
	
}
