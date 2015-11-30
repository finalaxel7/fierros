/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador_procesos.fierros;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author axel y wendy XD
 */
public class procesamiento {
    Timer timer = new Timer();
    int ID=1;
    memoria aux;
    memoria inicio=new memoria();
    memoria fin= new memoria();
    proceso nada= new proceso();
    int memtotal,memactualB=0,memactualF=0,memactualW=0, memmax,tiempomax,tiempolibre;
    memoria initialF, initialW, initialB;
    int veces;
    int vel=1000;
    int eliminadosF, eliminadosB, eliminadosW;
    public procesamiento(int mem, int maxm, int tiempo, int maxt){
    	memtotal=mem;
    	memmax=maxm;
    	tiempolibre=tiempo;
        veces=tiempo;
    	tiempomax=maxt;
    	initialF=new memoria(memtotal);
        
        inicio.setSig(initialF);
        initialF.setAnt(inicio);
        initialF.setSig(fin);
        fin.setAnt(initialF);
        memactualB=memtotal;
        memactualF=memtotal;
        memactualW=memtotal;
    }

    /*public static void main(String[] args) { 
        first holi= new first();
    }*/
    
    public void paginacion(){
        
    }
    
    public void segmentacion(){
        timer.schedule(new TimerTask() {
            public void run() {
              // Your database code here
                veces--;
                ID++;
                proceso nuevo= new proceso(ID , tiempomax, memmax);
                first (nuevo);
                
                
                if(veces ==0){
                     timer.cancel();
                }
            }
        }, vel, vel);
    }
    public void change_vel(boolean masmenos){//boton mas true boton menos false 
       if(masmenos==true){
           vel=(int)(vel * 1.5);
       }
       else{
          vel=(int)(vel/1.5);
       }
    }
    public void first(proceso nuevo ){
        boolean seencontro=false;
        proceso actual= new proceso();
        //actual = initialF;
        actual = initialF;
        while(actual.sig!=null){
            actual=actual.sig;
            if(actual.procedimiento.getTime>0){
                actual.procedimiento.time--;
                if(actual.procedimiento.getTime()==0){
                        actual.procedimiento.changeState();
                        memactualF=memactualF+actual.procedimiento.tamanio;
                }
            }
            do{
                if(actual.sig.procedimiento.getState==0 || actual.sig.procedimiento.getTime()==1){//SE CONDENSAN ESPACIOS VACIOS CONJUNTOS
                        memactualF=memactualF+actual.sig.procedimiento.tamanio;
                        actual.procedimiento.tamanio=actual.procedimiento.tamanio+actual.sig.procedimiento.tamanio;
                        actual.fin=actual.inicio+actual.procedimiento.tamanio;
                        actual.sig.sig.ant=actual;
                        actual.sig=actual.sig.sig;
                }
            }while(actual.sig.procedimiento.getState()==0 || actual.sig.procedimiento.getTime()==1);

            if(actual.procedimiento.getState==0 && seencontro==false){

                if(actual.procedimiento.tamanio==nuevo.tamanio){
                        actual.proceso=nuevo;
                        memactualF=memactualF-actual.proces.tamanio;
                        seencontro=true;
                }
                else if (actual.procedimiento.getMem() > nuevo.getMem()) {
                    memoria libre = new memoria(actual, actual.sig, (actual.procedimiento.getMem() - nuevo.getMem()), 0);
                    actual.procedimiento.tamanio = nuevo.tamanio;
                    actual.sig = libre;
                    actual.fin = actual.inicio + nuevo.tamanio;
                    libre.inicio = actual.fin + 1;
                    memactualF = memactualF - actual.procedimiento.tamanio;
                    seencontro = true;
                }

            }

        }

        if(seencontro==false){
                eliminadosF++;
        }
        //Pasar el segundo en el timer
	mostra_estadisticas();
    }
    
    public void worst(proceso nuevo ){
        boolean seencontro=false;
        proceso actual= new proceso();
        int dif;
        int max=-1;
        memoria auxiliar=null;

        
        actual = initialW;
        while(actual.sig!=null){
            actual=actual.sig;
            if(actual.procedimiento.getTime>0){
                actual.procedimiento.time--;
                if(actual.procedimiento.getTime()==0){
                        actual.procedimiento.changeState();
                        memactualW=memactualW+actual.procedimiento.tamanio;
                }
            }
            do{
                if(actual.sig.procedimiento.getState==0 || actual.sig.procedimiento.getTime()==1){//SE CONDENSAN ESPACIOS VACIOS CONJUNTOS
                        memactualW=memactualW+actual.sig.procedimiento.tamanio;
                        actual.procedimiento.tamanio=actual.procedimiento.tamanio+actual.sig.procedimiento.tamanio;
                        actual.fin=actual.inicio+actual.procedimiento.tamanio;
                        actual.sig.sig.ant=actual;
                        actual.sig=actual.sig.sig;
                }
            }while(actual.sig.procedimiento.getState()==0 || actual.sig.procedimiento.getTime()==1);

            if(actual.procedimiento.getState==0 && seencontro==false){
                if(actual.proceso.tamanio>nuevo.tamanio){
                    dif=actual.proceso.tamanio-nuevo.tamanio;
                    if(dif>max){
                        auxiliar=actual;
                        max=dif;
                    }
                }
            }

        }

        if(seencontro==false && auxiliar!=null){
            memoria libre = new memoria(actual, actual.sig, (actual.procedimiento.getMem() - nuevo.getMem()), 0);
            actual.proceso.tamanio=nuevo.tamanio;
            actual=auxiliar;
            actual.procedimiento.tamanio = nuevo.tamanio;
            actual.sig = libre;
            actual.fin = actual.inicio + nuevo.tamanio;
            libre.inicio = actual.fin + 1;
            memactualF = memactualF - actual.procedimiento.tamanio;
            seencontro = true;
        }
        else{
            eliminadosW++;
        }

    }
    
    public void best(proceso nuevo ){
        boolean seencontro=false;
        proceso actual= new proceso();
        //actual = initialF;
        actual = initialB;
        while(actual.sig!=null){
            actual=actual.sig;
            if(actual.procedimiento.getTime()>0){
                actual.procedimiento.time--;
                if(actual.procedimiento.getTime()==0){
                        actual.procedimiento.changeState();
                        memactualB=memactualB+actual.procedimiento.tamanio;
                }
            }
            do{
                if(actual.sig.procedimiento.getState==0 || actual.sig.procedimiento.getTime()==1){//SE CONDENSAN ESPACIOS VACIOS CONJUNTOS
                        memactualB=memactualB+actual.sig.procedimiento.tamanio;
                        actual.procedimiento.tamanio=actual.procedimiento.tamanio+actual.sig.procedimiento.tamanio;
                        actual.fin=actual.inicio+actual.procedimiento.tamanio;
                        actual.sig.sig.ant=actual;
                        actual.sig=actual.sig.sig;
                }
            }while(actual.sig.procedimiento.getState()==0 || actual.sig.procedimiento.getTime()==1);

            if(actual.procedimiento.getState==0 && seencontro==false){

                if(actual.procedimiento.tamanio==nuevo.tamanio){
                        actual.proceso=nuevo;
                        memactualB=memactualB-actual.proces.tamanio;
                        seencontro=true;
                }
                else if (actual.procedimiento.getMem() > nuevo.getMem()) {
                    memoria libre = new memoria(actual, actual.sig, (actual.procedimiento.getMem() - nuevo.getMem()), 0);
                    actual.procedimiento.tamanio = nuevo.tamanio;
                    actual.sig = libre;
                    actual.fin = actual.inicio + nuevo.tamanio;
                    libre.inicio = actual.fin + 1;
                    memactualB = memactualB - actual.procedimiento.tamanio;
                    seencontro = true;
                }

            }

        }

        if(seencontro==false){
                eliminadosF++;
        }
        //Pasar el segundo en el timer
	mostra_estadisticas();
    }
    
    
    public void mostra_estadisticas(){

    }


}



class first{
    int veces;
    public first(){
        veces=10;
        segmentacion();
    }
    Timer timer = new Timer();
    public void segmentacion(){
        timer.schedule(new TimerTask() {
            public void run() {
              // Your database code here
                veces--;
                System.out.println("holi");
                if(veces ==0){
                     timer.cancel();
                }
            }
        }, 1*1000, 1*1000);
        
    }
}
