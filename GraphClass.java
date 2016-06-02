class Edge {
    Vertex dest;
    double cost;
    public Edge(Vertex d, double c) {
        dest = d;
        cost = c;
    }
}

class Vertex {
    int name,size,dist,decompositionId;
    Vertex parent;
    boolean visited;
    List<Edge> adj;
    public Vertex(int n) {
        name = n;
        adj = new LinkedList<Edge>();
        decompositionId = -1;
    }
}

class Graph {
    private Map<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();
    //Decomposition ID Map
    Map<Integer, Vertex> decIdMap = new HashMap<Integer, Vertex>();
    int ID;
    public void reset() {
    }       

    public Vertex getVertex(int vName) {
        Vertex v = vertexMap.get(vName);
        if (v == null) {
            v = new Vertex(vName);
            vertexMap.put(vName, v);
        }
        return v;
    }

    public void addEdge(int begin, int end, double c) {
        Vertex v = getVertex(begin);
        Vertex w = getVertex(end);
        v.adj.add(new Edge(w, c));
        //By Directional
        w.adj.add(new Edge(v, c));
    }
    
    public void saveParantAndSubTreeSize(int n) {
        Vertex v = getVertex(n);
        Stack<Vertex> s = new Stack<Vertex>(), sp = new Stack<Vertex>();
        s.add(v);
        boolean isLeaf;
        Vertex w;
        while(!s.isEmpty()) {
            v=s.pop();
            isLeaf = true;
            for(Edge e : v.adj)
                if((w = e.dest) != v.parent) {
                    isLeaf = false;
                    w.parent = v;
                    w.dist = v.dist + 1;
                    s.add(w);
                }
            if(isLeaf)
                sp.add(v);
        }
        while(!sp.isEmpty()) {
            v=sp.pop();
            v.size++;
            if(v.parent != null) {
                v.parent.size = v.size;
                sp.add(v.parent);
            }
        }
    }
    
    public void decompositionBuild(int n) {
        Vertex v = getVertex(n);
        Stack<Vertex> s = new Stack<Vertex>();
        s.add(v);
        int max;
        Vertex cur, w;
        while(!s.isEmpty()) {
            v = s.pop();
            if(v.decompositionId == -1) {
                v.decompositionId = ID;
                decIdMap.put(ID++, v);
            }
            max =-1;
            cur = null;
            for(Edge e : v.adj)
                if((w = e.dest) != v.parent && max < w.size) {
                    max = w.size;
                    cur = w;
                }
            if(max > -1) {
                cur.decompositionId = v.decompositionId;
                s.add(cur);
            }
            for(Edge e : v.adj)
                if((w = e.dest) != v.parent && w != cur)
                    s.add(w);
        }
    }
    
    public long getDecompositionDestonationBetween(int n1, int n2) {
        Vertex v = getVertex(n1);
        Vertex w = getVertex(n2);
        Vertex c = LCA(v, w);
        return v.dist + w.dist - 2 * c.dist;
    }
    
    public Vertex LCA(int v, int w) {
        return LCA(getVertex(v), getVertex(w));
    }
    
    public Vertex LCA(Vertex v, Vertex w) {
        Vertex nv = null, nw = null;
        while(v.decompositionId != w.decompositionId) {
            if(decIdMap.get(v.decompositionId) == v)
                nv = v.parent;
            else nv = decIdMap.get(v.decompositionId);
            if(decIdMap.get(w.decompositionId) == w)
                nw = w.parent;
            else nw = decIdMap.get(w.decompositionId);
            if(nv == null || nw != null && nv.dist < nw.dist)
                w = nw;
            else v = nv;
        }
        if(v.dist < w.dist)
            return v;
        return w;
    }
}