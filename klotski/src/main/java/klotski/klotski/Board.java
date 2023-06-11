package klotski.klotski;

import java.util.Vector;

public class Board{
    //contiene un numero che indica la configurazione scelta
    private int conf;
    //lista di coordinate dei blocchi in ordine (4 blocchi piccoli, 3-4 1x2, 1-2 blocchi 2x1, 1 blocco 2x2)
    private Coordinates[] board;
    private String bestSolution;
    private final Vector<String> bestSolutionSequence;

    public Board(int conf){
        this.conf = conf;
        bestSolutionSequence = new Vector<>();
        reset();
    }
    //costruttore per generare una board con le stesse caratteristiche di quella di input
    public Board(Board board){
        conf = board.getConf();
        int cont = 0;
        this.board = new Coordinates[10];
        for(Coordinates coord : board.getBoard())
            this.board[cont++] = new Coordinates(coord.getX(), coord.getY());
        this.bestSolution = board.getBestSolution();
        this.bestSolutionSequence = board.getBestSolutionSequence();
    }

    public void reset(){
        //in base alla configurazione scelta resetto la board
        switch(conf){
            case 1 -> setConfiguration1();
            case 2 -> setConfiguration2();
            case 3 -> setConfiguration3();
            case 4 -> setConfiguration4();
        }
    }

    public void setConfiguration1(){
        conf = 1;
        //4 piccoli, 4 verticali, 1 orizzontale, 1 grande
        board = new Coordinates[]{new Coordinates(102, 202),new Coordinates(202, 202), new Coordinates(102, 302), new Coordinates(202, 302),
                new Coordinates(2, 2), new Coordinates(2, 202), new Coordinates(302, 2), new Coordinates(302, 202),
                new Coordinates(102, 402),
                new Coordinates(102, 2)};
        bestSolution = "8a 3s 3d 1s 1s 2d 0d 5d " +
                        "4s 4s 9a 0w 0w 2w 2w 1w 1w 3a 3w 8d 8d " +
                        "5s 4s 9s 0a 0a 2w 2a 1w 1w 9d " +
                        "4w 4w 5a 3a 3s 9s 1s 1a 6a " +
                        "7w 7w 9d 1s 2s 0d 4w 5w 3a " +
                        "1s 1s 9a 7s 7s 6d 2d 0d 4d 5w 5w " +
                        "9a 2s 2s 0s 0s 6a 7w 7w 2d 2w 8w " +
                        "1d 1d 3d 3d 9s 0a 0a 2a 2a 8w 3w 3d " +
                        "9d 9s";
        refreshBestSolutionSequence();
    }
    public void setConfiguration2(){
        conf = 2;
        //4 piccoli, 3 verticali, 2 orizzontali, 1 grande
        board = new Coordinates[]{new Coordinates(2, 2), new Coordinates(302, 2), new Coordinates(2, 302), new Coordinates(302, 302),
                new Coordinates(2, 102), new Coordinates(302, 102), new Coordinates(102, 202),
                new Coordinates(2, 402), new Coordinates(202, 402),
                new Coordinates(102, 2)};
        bestSolution = "3a 3w 8w 7d 7d 2s 2d 4s 4s 0s 0s 9a 1a 5w 3d 1s 1s 9d 0w 0w 4w 4w 2a 2w " +
                        "7a 7a 8s 3s 3a 5s 5s 9d 6w 6w 2d 2w 7w 8a 8a 3s 3d 7d 8d 4s 4s 0s 0s 6a 2w 2w 0d 6s " +
                        "2a 9a 5w 5w 7d 0s 1a 7w 3w 0d 8d 4d 6s 6s 1a 1w 7a 7a 3w 3a 5s 5s 9d 2d 1w " +
                        "7w 6w 4w 8a 8a 0s 3s 5s 9s 2d 2d 1d 1d 7w 4w 3a 0w 8d 6s 4a 9a " +
                        "2s 1d 5w 7d 4w 6w 8d 3s 3a 0a 0s 9s 2a 1s 7d 4d 6w 6w 9a 2s 1a " +
                        "5w 8w 0d 0d 3d 3d 9s 2a 2a 4s 7a 5w 8w 3w 3d " +
                        "9d 9s";
        refreshBestSolutionSequence();
    }
    public void setConfiguration3(){
        conf = 3;
        //4 piccoli, 3 verticali, 2 orizzontali, 1 grande
        board = new Coordinates[]{new Coordinates(102, 2), new Coordinates(202, 2), new Coordinates(302, 2), new Coordinates(302, 302),
                new Coordinates(2, 2), new Coordinates(102, 102), new Coordinates(2, 202),
                new Coordinates(102, 302), new Coordinates(202, 402),
                new Coordinates(202, 102)};
        bestSolution = "8a 3s 6s 4s 7d 5s 0s 1a 1a 2a 2a 9w 7w 3w 8d 5s 0s 2s 1d 4w 0a 5w " +
                        "8a 3s 7s 9s 1d 1d 2w 2d 5w 5w 0d 0s 4s 5s 2a 2a 1a 1a 9w 7w 3w 8d 0s 3a 3a 7s 9s 1d 1d 2d 2d 4w 5w " +
                        "6w 3w 0w 8a 8a 7s 0d 0d 3s 3d 5s 5s 2a 2s 1a 1a 9w 3w 3d 5d 6d 4s 1a 2w 6w 8w 7a 7a 0s 0a 3s 3s 5d 0w 0w " +
                        "7d 8d 4s 4s 6a 2s 2s 9a 5w 5w 8d 2s 0a 8w 2d 3w 7d 4d 6s 6s 0a 0w 8a 8a 3w 3a 5s 5s 9d 0d 0w 8w 4w " +
                        "6w 7a 7a 2s 3s 5s 9s 0d 0d 1d 1d 8w 4w 3a 2w 7d 6s 4a 9a 0s 1d 5w 7d 8d 4w 6w 3s 3a " +
                        "2a 2s 9s 0a 1s 8d 4d 6w 6w 9a 0s 1a 5w 7w 2d 2d 3d 3d 9s 0a 0a 4s 8a 5w 7w 3w 3d " +
                        "9d 9s";
        refreshBestSolutionSequence();
    }
    public void setConfiguration4(){
        conf = 4;
        //4 piccoli, 4 verticali, 1 orizzontali, 1 grande
        board = new Coordinates[]{new Coordinates(102, 302), new Coordinates(202, 302), new Coordinates(2, 402), new Coordinates(302, 402),
                new Coordinates(2, 2), new Coordinates(302, 2), new Coordinates(2, 202), new Coordinates(302, 202),
                new Coordinates(102, 202),
                new Coordinates(102, 2)};
        bestSolution = "2d 3a 6s 7s 8a 1w 1d 3w 3w 7a 1s 1s 3d 3s 8d 8d " +
                        "0w 0a 2w 2w 7a 3a 3s 8s 2d 2d 0d 0d 6w 7w 3a 3a 1a 1a 8s 0s 0d " +
                        "7d 1w 1w 3d 3w 8a 8a 0s 0a 2s 2s 7d 1d 3d 6d 4s 4s 9a 1w 1w 3w 3w 0w 0w 2a 2w 8d 8d 4s 6s 9s 1a 1a 3w 3a 0w 0w " +
                        "9d 4w 4w 6a 2a 2s 9s 0s 0a 5a 7w 7w 9d 0s 0s 3s 1d 4w 6w 2a 0s 9a 7s 7s 5d 1d 3d 4d 6w 6w 9a " +
                        "3s 3s 1s 1s 5a 7w 7w 1d 3w 8w 0d 0d 2d 2d 9s 3a 3a 1a 1a 8w 0w 2d " +
                        "9d 9s";
        refreshBestSolutionSequence();
    }

    private void refreshBestSolutionSequence(){
        Board tmp = new Board(this);
        bestSolutionSequence.clear();
        bestSolutionSequence.add(tmp.toString());
        //popola il vettore bestSolutionSequence con le varie configurazioni
        for(String move : bestSolution.split(" ")){
            tmp.evaluate(move.charAt(0) - 48, move.charAt(1));
            bestSolutionSequence.add(tmp.toString());
        }
    }

    public Coordinates get(int index){
        return board[index];
    }

    public int getConf(){
        return conf;
    }

    public String getBestSolution(){
        return bestSolution;
    }

    public Vector<String> getBestSolutionSequence(){
        return bestSolutionSequence;
    }

    public Coordinates[] getBoard(){ return board; }

    //cerca il blocco selezionato cliccando nelle coordinate x e y
    public int search(int x, int y){
        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;
        int index = 0;
        boolean found = false;
        //variano se il blocco è più grande di 1x1
        int xoffset = 0;
        int yoffset = 0;

        while(index < 10 && !found){
            //aggiorno i vari offset per la ricerca (in base alla dimensione del blocco)
            if(index == 9){
                xoffset = -100;
                yoffset = -100;
            }else {
                if (index >= confDifference) {
                    xoffset = -100;
                    yoffset = 0;
                } else if (index > 3) {
                    yoffset = -100;
                    xoffset = 0;
                }
            }

            if((x - x % 100 + 2 == board[index].getX() || x - x % 100 + 2 + xoffset == board[index].getX()) &&
                    (y - y % 100 + 2 == board[index].getY() || y - y % 100 + 2 + yoffset == board[index].getY()))
                found = true;
            else
                index++;
        }

        return found ? index : -1;
    }

    public String evaluate(Coordinates dragStart, Coordinates dragEnd){
        //ricerca del blocco selezionato
        int index = search(dragStart.getX(),dragStart.getY());
        //se c'è
        if(index != -1){
            //calcolo la posizione differenziale
            int xDifference = dragEnd.getX() - dragStart.getX();
            int yDifference = dragEnd.getY() - dragStart.getY();
            //deduco la mossa
            int xoffset = 0;
            int yoffset = 0;
            char move;
            if(Math.abs(xDifference) > Math.abs(yDifference)){
                if(xDifference > 0){
                    xoffset = 100;
                    move = 'd';
                }else{
                    xoffset = -100;
                    move = 'a';
                }
            }else{
                if(yDifference > 0){
                    yoffset = 100;
                    move = 's';
                }else{
                    yoffset = -100;
                    move = 'w';
                }
            }
            if(checkwin(index, move))
                return "win";
            //attuo la mossa, se è possibile
            if(move(index, xoffset, yoffset))
                return Integer.toString(index) + move;

            return null;
        }
        return null;
    }

    public String evaluate(int index, char move){
        //controllo se ho vinto
        if(index == 9 && board[index].getX() == 102 && board[index].getY() == 302 && move == 's')
            return "win";

        int xoffset = 0;
        int yoffset = 0;
        //aggiorno gli offset in base alla mossa
        switch(move){
            case 'w' -> yoffset = -100;
            case 'a' -> xoffset = -100;
            case 's' -> yoffset = 100;
            case 'd' -> xoffset = 100;
        }

        //attuo la mossa, se è possibile
        if(move(index, xoffset, yoffset))
            return Integer.toString(index) + move;

        return null;
    }

    private boolean move(int index, int xoffset, int yoffset) {
        //necessaria a causa della quantità di blocchi 1x2 e 2x1 variabile nelle varie configurazioni
        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;
        //aumenta se un blocco è lungo/alto 2 e vuole spostarsi a destra/in basso
        int xoffset2 = 0;
        int yoffset2 = 0;
        //aumenta se il blocco è lungo/alto 2 e vuole spostarsi verticalmente/orizzontalmente
        int xoffset3 = 0;
        int yoffset3 = 0;

        if(index == 9){
            if(xoffset != 0){
                yoffset3 = 100;
                if(xoffset > 0)
                    xoffset2 = 100;
            }
            if(yoffset != 0){
                xoffset3 = 100;
                if(yoffset > 0)
                    yoffset2 = 100;
            }
        }else{
            if(index >= confDifference){
                if(xoffset > 0)
                    xoffset2 = 100;
                else
                if(xoffset == 0)
                    xoffset3 = 100;
            }else
            if(index > 3){
                if(yoffset > 0)
                    yoffset2 = 100;
                else
                if(yoffset == 0)
                    yoffset3 = 100;
            }
        }
        //se la mossa è possibile aggiorno la posizione del blocco
        if(search(board[index].getX() + xoffset + xoffset2, board[index].getY() + yoffset + yoffset2) == -1 &&
                search(board[index].getX() + xoffset + xoffset2 + xoffset3, board[index].getY() + yoffset + yoffset2 + yoffset3) == -1 &&
                board[index].getX() + xoffset > 0 && board[index].getX() + xoffset + xoffset2 < 400 &&
                board[index].getY() + yoffset > 0 && board[index].getY() + yoffset + yoffset2 < 500){
            board[index].incX(xoffset);
            board[index].incY(yoffset);
            return true;
        }
        return false;
    }

    public boolean checkwin(int index, char move){
        //controllo se ho vinto
        return (index == 9 && board[index].getX() == 102 && board[index].getY() == 302 && move == 's');
    }

    public Vector<String> getPossibleMoves(){
        Vector<String> out = new Vector<>();
        //necessario a causa del numero variabile di blocchi 1x2 e 2x1
        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;

        int xoffset = 100;
        int yoffset = 100;
        int xoffset2 = 0;
        int yoffset2 = 0;

        for(int i = 0; i < 10; i++){
            if(i == 9){
                xoffset = 200;
                yoffset = 200;
                xoffset2 = 100;
                yoffset2 = 100;
            }else{
                if(i >= confDifference){
                    xoffset = 200;
                    yoffset = 100;
                    xoffset2 = 100;
                    yoffset2 = 0;
                }else{
                    if(i > 3){
                        xoffset = 100;
                        yoffset = 200;
                        xoffset2 = 0;
                        yoffset2 = 100;
                    }
                }
            }

            if(board[i].getX() > 100 && search(board[i].getX() - 100, board[i].getY()) == -1 && search(board[i].getX() - 100, board[i].getY() + yoffset2) == -1)
                out.add(Integer.toString(i) + 'a');
            if(board[i].getX() + xoffset < 400 && search(board[i].getX() + xoffset, board[i].getY()) == -1 && search(board[i].getX() + xoffset, board[i].getY() + yoffset2) == -1)
                out.add(Integer.toString(i) + 'd');
            if(board[i].getY() > 100 && search(board[i].getX(), board[i].getY() - 100) == -1 && search(board[i].getX() + xoffset2, board[i].getY() - 100) == -1)
                out.add(Integer.toString(i) + 'w');
            if(board[i].getY() + yoffset < 500 && search(board[i].getX(), board[i].getY() + yoffset) == -1 && search(board[i].getX() + xoffset2, board[i].getY() + yoffset) == -1)
                out.add(Integer.toString(i) + 's');
        }

        return out;
    }
    //metodo che ritorna la board in formato stringa (0 = vuoto, 1 = blocco 1x1, 2 = blocco 1x2, 3 = blocco 2x1, 4 = blocco 2x2)
    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();

        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;

        for(int i = 0; i < 20; i++)
            switch(search((i%4) * 100 + 2, (i/4) * 100 + 2)){
                case -1 -> out.append("0");
                case 0, 1, 2, 3 -> out.append("1");
                case 4, 5, 6 -> out.append("2");
                case 7 -> out.append(confDifference == 7 ? "3" : "2");
                case 8 -> out.append("3");
                case 9 -> out.append("4");
            }

        return out.toString();
    }
    //metodo che ritorna la board specchiata in formato stringa
    public String toInverseString(){
        StringBuilder out = new StringBuilder();

        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;

        for(int i = 0; i < 5; i++)
            for(int j = 3; j >= 0; j--){
                int block = search(j*100 + 10, i*100 + 10);
                switch(block){
                    case -1 -> out.append('0');
                    case 0,1,2,3 -> out.append('1');
                    case 4,5,6 -> out.append('2');
                    case 7 -> {if(confDifference == 7) out.append('3'); else out.append('2');}
                    case 8 -> out.append('3');
                    case 9 -> out.append('4');
                }
            }

        return out.toString();
    }
    //metodo che trasforma una stringa in una board
    public static Board strToBoard(String in, int conf){
        Board out = new Board(conf);

        int confDifference = 7;
        if(conf == 1 || conf == 4)
            confDifference = 8;

        int i = 0;
        int cont1 = 0;
        int cont2 = 0;
        int cont3 = 0;
        while(i < in.length()){
            switch(in.charAt(i)){
                case '1'-> {
                    out.get(cont1).setX((i%4) *100 + 2);
                    out.get(cont1++).setY((i/4) * 100 + 2);
                    if(i < 19)
                        in = in.substring(0,i) + '0' + in.substring(i+1);
                    else
                        in = in.substring(0,i) + '0';
                }
                case '2'-> {
                    out.get(4 + cont2).setX((i%4) * 100 + 2);
                    out.get(4 + cont2++).setY((i/4) * 100 + 2);
                    if(i < 15)
                        in = in.substring(0, i) + '0' + in.substring(i+1,i+4) + '0' + in.substring(i+5);
                    else
                        in = in.substring(0, i) + '0' + in.substring(i+1,i+4) + '0';
                }
                case '3' -> {
                    out.get(confDifference + cont3).setX((i%4) *100 + 2);
                    out.get(confDifference + cont3++).setY((i/4) * 100 + 2);
                    if(i < 18)
                        in = in.substring(0,i) + "00" + in.substring(i+2);
                    else
                        in = in.substring(0,i) + "00";
                }
                case '4' -> {
                    out.get(9).setX((i%4) *100 + 2);
                    out.get(9).setY((i/4) * 100 + 2);
                    if(i < 14)
                        in = in.substring(0,i) + "00" + in.substring(i+2, i+4) + "00" + in.substring(i+6);
                    else
                        in = in.substring(0,i) + "00" + in.substring(i+2, i+4) + "00";
                }
            }
            i++;
        }

        return out;
    }

    @Override
    public boolean equals(Object obj){
        if(!obj.getClass().equals(this.getClass()))
            return false;

        if(conf != ((Board) obj).getConf())
            return false;

        return obj.toString().equals(toString());
    }
}