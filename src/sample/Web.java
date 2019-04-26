package sample;

class Web{
    public int mul[][];
    public int weight[][];
    public int input[][];
    public int limit = 9;
    public int sum;


    public Web(int sizeX, int sizeY, int inP[][]){

        mul = new int[sizeX][sizeY];

        weight = new int[sizeX][sizeY];

        input = new int[sizeX][sizeY];

        input = inP;

    }


    public void mul_w()
    {
        for (int x = 0; x <= 2; x++)
        {
            for (int y = 0; y <= 4; y++)
            {
                mul[x][y] = input[x][y]*weight[x][y];
            }
        }
    }


    public void Sum()
    {
        sum = 0;
        for (int x = 0; x <= 2; x++)
        {
            for (int y = 0; y <= 4; y++)
            {
                sum += mul[x][y];
            }
        }
    }


    public boolean Rez()
    {
        if (sum >= limit)
            return true;
        else return false;
    }


    public void incW(int inP[][])
    {
        for (int x = 0; x <= 2; x++)
        {
            for (int y = 0; y <= 4; y++)
            {
                weight[x][y] += inP[x][y];
            }
        }
    }

    public void decW(int inP[][])
    {
        for (int x = 0; x <= 2; x++)
        {
            for (int y = 0; y <= 4; y++)
            {
                weight[x][y] -= inP[x][y];
            }
        }
    }

}

