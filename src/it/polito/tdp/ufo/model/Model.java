package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private List<String> stati;
	private Graph<String, DefaultEdge> grafo;
	
	//PER LA RICORSIONE
	
	//1. struttura dati finale
	private List<String> ottima; //lista di stati(STRING) in cui 
	//c'è lo stato di partenza ed un insieme di altri stati non ripetuti
	
	//2.struttura dati parziale nel metodo ricorsivo
	
	//3 condizione di terminazione
	//dopo un determinato nodo non ci sono più successori che non ho considerato
	
	//4. generare una nuova soluzione a partire da una soluzione parziale
	//dato l'ultimo nodo inserito in parziale, considero tutti i successori di quel nodo
	//che non ho ancora considerato
	
	//5. filtro
	//alla fine ritornerò una sola soluzione -> quella per cui size() è massima
	
	//6. livello di ricorsione
	//lunghezza del percorso parziale
	
	//7. il caso iniziale
	//parziale contiene il mio stato di partenza
	
	
	public Model() {
		this.dao = new SightingsDAO();
	}
	
	public List<AnnoCount> getAnni(){
		return this.dao.getAnni();
	}
	
	public void creaGrafo(Year anno) {
		
		this.stati = this.dao.getStati(anno);
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.stati);
		
		//soluzione semplice: doppio ciclo, controllo esistenza arco
		
		for(String s1 : this.grafo.vertexSet()) {
			for(String s2 : this.grafo.vertexSet()) {
				if(!s1.equals(s2)) {
					if(this.dao.esisteArco(s1, s2, anno)) {
						this.grafo.addEdge(s1, s2);
					}
				}
			}
		}
		
		System.out.println("Grafo creato!");
		System.out.println("vertici: "+ this.grafo.vertexSet().size());
		System.out.println("archi: "+this.grafo.edgeSet().size());
		
	}
	
	public int getNvertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNarchi() {
		return this.grafo.edgeSet().size();
	}

	public List<String> getStati() {
		Collections.sort(this.stati);
		return this.stati;
	}
	
	public List<String> getSuccessori(String stato){
		return Graphs.successorListOf(this.grafo, stato);
		
	}
	
	public List<String> getPredecessori(String stato){
		return Graphs.predecessorListOf(this.grafo, stato);
	}
	
	
	public List<String> getRaggiungibili(String stato){
		List<String> raggiungibili = new ArrayList<>();
		DepthFirstIterator<String, DefaultEdge> dp = new DepthFirstIterator<>(this.grafo, stato);
		
		dp.next();//per non considerare il nodo di partenza 
		while(dp.hasNext()) {
			raggiungibili.add(dp.next());
		}
	
		
		return raggiungibili;
	}
	
	public List<String> getPercorsoMax(String partenza){
		
		this.ottima = new ArrayList<>();		
		List<String> parziale = new ArrayList<>();
		
		parziale.add(partenza);
		
		cercaPercorso(parziale);
		
		return this.ottima;
	}

	private void cercaPercorso(List<String> parziale) {
		
		//vedere se la soluzione corrente è migliore della ottima corrente
		if(parziale.size() > ottima.size()) {
			this.ottima = new ArrayList<>(parziale);
		}
		
		List<String> candidati = this.getSuccessori(parziale.get(parziale.size() - 1));
		for(String candidato : candidati) {
			if(!parziale.contains(candidato)) {
				//è un candidato che non ho ancora considerato
				parziale.add(candidato);
				cercaPercorso(parziale);
				parziale.remove(parziale.size() - 1);
			}
		}
		
	}
	

}
