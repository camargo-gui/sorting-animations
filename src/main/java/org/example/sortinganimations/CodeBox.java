package org.example.sortinganimations;

public class CodeBox {
    private String [] codeLines;

    public String [] getCodeLines(){
        return codeLines;
    }

    public CodeBox (String method){
        if (method.compareTo("ShellSort") == 0) {
            codeLines = new String[]{
                    "public void shell_sort() {", //0
                    "    int dist = 1, i, j, aux;", //1
                    "", //2
                    "    while (dist < TL) {", //3
                    "        dist = dist * 3 , 1;", //4
                    "    }", //5
                    "    dist /= 3;", //6
                    "", //7
                    "    while (dist > 0) {", //8
                    "        for (i = dist; i < TL; i,,) {", //9
                    "            aux = array[i];", //10
                    "            j = i;", //11
                    "            while (j - dist >= 0 && aux < array[j - dist]) {", //12
                    "                array[j] = array[j - dist];", //13
                    "                j = j - dist;", //14
                    "            }", //15
                    "            array[j] = aux;", //16
                    "        }", //17
                    "        dist /= 3;", //18
                    "    }", //19
                    "}" //20
            };
        }
        else {
            codeLines = new String[] {
                            "    public void counting_sort(){" ,
                            "        //find the major" ,
                            "        int major = 0, pos;" ,
                            "        for(int i=0; i<TL; i,,){" ,
                            "            if(array[i] > major){" ,
                            "                major = array[i];" ,
                            "            }" ,
                            "        }" ,
                            "        int [] B = new int[major , 1], C = new int[major , 1];" ,
                            " " ,
                            "        //count" ,
                            "        for(int i=0; i<TL; i,,){" ,
                            "            B[array[i]] ,= 1;" ,
                            "        }" ,
                            "" ,
                            "        //cumulative" ,
                            "        for(int i=1; i<=major; i,,){" ,
                            "            B[i] = B[i] , B[i-1];" ,
                            "        }" ,
                            "" ,
                            "        for(int i=TL-1; i >= 0; i--){" ,
                            "            pos = B[array[i]];" ,
                            "            B[array[i]] -= 1;" ,
                            "            C[pos] = array[i];" ,
                            "        }" ,
                            "        array = C;" ,
                            "    }",
            };
        }
    }
}

