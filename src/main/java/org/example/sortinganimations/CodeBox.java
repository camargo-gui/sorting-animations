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
                    "        dist = dist * 3 + 1;", //4
                    "    }", //5
                    "    dist /= 3;", //6
                    "", //7
                    "    while (dist > 0) {", //8
                    "        for (i = dist; i < TL; i++) {", //9
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
                            "    public void counting_sort(){" , //0
                            " " , //1
                            "        int major = 0, pos;" , //2
                            "        for(int i=0; i<TL; i++){" , //3
                            "            if(array[i] > major){" , //4
                            "                major = array[i];" , //5
                            "            }" , //6
                            "        }" , //7
                            "        int [] B = new int[major + 1], C = new int[TL];" , //8
                            " " , //9
                            "        for(int i=0; i<TL; i++){" , //10
                            "            B[array[i]] += 1;" , //11
                            "        }" , //12
                            "" , //13
                            "        for(int i=1; i<=major; i++){" , //14
                            "            B[i] = B[i] + B[i-1];" , //15
                            "        }" , //16
                            "" , //17
                            "        for(int i=TL-1; i >= 0; i--){" , //18
                            "            pos = B[array[i]] - 1;" , //19
                            "            B[array[i]] -= 1;" , //20
                            "            C[pos] = array[i];" , //21
                            "        }" , //22
                            "        array = C;" , //23
                            "    }", //24
            };
        }
    }
}

