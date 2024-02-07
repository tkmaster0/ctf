package implementations;

import interfaces.NetworkADT;
import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {

    private double[][] weights; // Matriz para armazenar os pesos das arestas

    public Network() {
        super(); // Chama o construtor da classe Graph
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.weights = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
        initializeWeights();
    }

    private void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addVertex(vertex1); // Garante que ambos os vértices estão presentes
        addVertex(vertex2);
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
            weights[index1][index2] = weight;
            weights[index2][index1] = weight;
        }
    }

    protected void expandCapacity() {
        int newCapacity = vertices.length * 2;
        T[] newVertices = (T[]) new Object[newCapacity];
        boolean[][] newAdjMatrix = new boolean[newCapacity][newCapacity];
        double[][] newWeights = new double[newCapacity][newCapacity];

        for (int i = 0; i < numVertices; i++) {
            newVertices[i] = vertices[i];
            for (int j = 0; j < numVertices; j++) {
                newAdjMatrix[i][j] = adjMatrix[i][j];
                newWeights[i][j] = weights[i][j];
            }
        }

        for (int i = numVertices; i < newCapacity; i++) {
            for (int j = numVertices; j < newCapacity; j++) {
                newWeights[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        vertices = newVertices;
        adjMatrix = newAdjMatrix;
        weights = newWeights;
    }

    /**
     * Returns a minimum spanning tree of the network.
     *
     * @return a minimum spanning tree of the network
     */
    public Network<T> mstNetwork() {
        Network<T> mst = new Network<>();
        if (isEmpty()) {
            return mst; // Retorna MST vazio se o grafo original estiver vazio
        }
        boolean[] visited = new boolean[numVertices]; // Track visited vertices
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Start with the first vertex and consider all its edges
        visited[0] = true;
        addAllEdges(0, visited, pq);

        while (!pq.isEmpty() && mst.numVertices < numVertices) {
            Edge edge = pq.poll(); // Get the lowest weight edge
            if (visited[edge.source] && visited[edge.dest]) {
                continue; // Skip if both vertices are already visited
            }
            // The new vertex to be visited next
            int newIndex = visited[edge.source] ? edge.dest : edge.source;
            visited[newIndex] = true;
            mst.addVertex(vertices[newIndex]); // Assuming addVertex handles duplicates gracefully
            mst.addEdge(vertices[edge.source], vertices[edge.dest], edge.weight);
            addAllEdges(newIndex, visited, pq);
        }

        return mst;
    }

    // Helper method to add all edges from a given vertex to the priority queue
    private void addAllEdges(int vertexIndex, boolean[] visited, PriorityQueue<Edge> pq) {
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && weights[vertexIndex][i] < Double.POSITIVE_INFINITY) {
                pq.add(new Edge(vertexIndex, i, weights[vertexIndex][i]));
            }
        }
    }

    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        // Verifica se os índices são válidos
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return Double.POSITIVE_INFINITY; // Retorna "infinito" se os vértices não são válidos ou não estão no grafo.
        }

        double[] distances = new double[numVertices]; // Armazena as distâncias mínimas do startVertex
        boolean[] visited = new boolean[numVertices]; // Marca os vértices visitados durante o algoritmo

        // Inicializa as distâncias e o estado de visitado
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }
        distances[startIndex] = 0; // A distância até o próprio startVertex é 0

        PriorityQueue<PriorityQueueNode<T>> pq = new PriorityQueue<>();
        pq.addElement(startVertex, 0); // Adiciona o vértice inicial à fila de prioridades com prioridade 0

        while (!pq.isEmpty()) {
            PriorityQueueNode<T> pqNode = pq.removeNext(); // Remove o vértice com a menor distância (prioridade)
            T currentVertex = pqNode.getElement();
            int currentIndex = getIndex(currentVertex);

            // Se o vértice destino foi alcançado, retorna a distância calculada
            if (currentIndex == targetIndex) {
                return distances[targetIndex];
            }

            // Se o vértice atual ainda não foi visitado
            if (!visited[currentIndex]) {
                visited[currentIndex] = true;

                // Atualiza a distância para todos os vértices adjacentes
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[currentIndex][i] && !visited[i]) {
                        double edgeWeight = weights[currentIndex][i];
                        double newDist = distances[currentIndex] + edgeWeight;

                        if (newDist < distances[i]) {
                            distances[i] = newDist;
                            pq.addElement(vertices[i], (int) newDist); // Adiciona/atualiza o vértice na fila de prioridades
                        }
                    }
                }
            }
        }

        // Retorna a distância até o targetVertex, ou "infinito" se não houver caminho
        return distances[targetIndex];
    }

    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);
        if (indexIsValid(index)) {
            numVertices--;

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j <= numVertices; j++) {
                    adjMatrix[i][j] = adjMatrix[i + 1][j];
                    weights[i][j] = weights[i + 1][j];
                }
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j <= numVertices; j++) {
                    adjMatrix[j][i] = adjMatrix[j][i + 1];
                    weights[j][i] = weights[j][i + 1];
                }
            }
        }
    }

    // Implementação de removeEdge
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
            weights[index1][index2] = Double.POSITIVE_INFINITY;
            weights[index2][index1] = Double.POSITIVE_INFINITY;
        }
    }

    // Implementação de isEmpty
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    // Implementação de isConnected
    @Override
    public boolean isConnected() {
        for (int i = 0; i < numVertices; i++) {
            int count = 0;
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    count++;
                }
                if (count >= numVertices - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // Implementação de size
    @Override
    public int size() {
        return numVertices;
    }

    // Implementação de toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Network: \n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(vertices[i].toString()).append(": ");
            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j]) {
                    sb.append(vertices[j].toString()).append(" (").append(weights[i][j]).append("), ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        LinkedQueue<T> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        int startIndex = getIndex(startVertex);

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startVertex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            T vertex = traversalQueue.dequeue();
            resultList.addToRear(vertex);
            int vertexIndex = getIndex(vertex);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[vertexIndex][i] && !visited[i]) {
                    traversalQueue.enqueue(vertices[i]);
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        LinkedStack<T> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];
        int startIndex = getIndex(startVertex);

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        traversalStack.push(startVertex);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            T vertex = traversalStack.pop();
            resultList.addToRear(vertex);

            int vertexIndex = getIndex(vertex);
            // Marca como encontrado para garantir que não processamos duas vezes
            if (!visited[vertexIndex]) {
                visited[vertexIndex] = true;
            }

            // Encontra todos os vértices adjacentes não visitados e empilha
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[vertexIndex][i] && !visited[i]) {
                    traversalStack.push(vertices[i]);
                    // Não marca como visitado aqui; isso acontece no próximo ciclo
                }
            }
        }

        return resultList.iterator();
    }

    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.equals(vertices[i])) {
                return i; // Retorna o índice do vértice encontrado
            }
        }
        return -1; // Retorna -1 se o vértice não for encontrado
    }

    public boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices; // Verifica se o índice está dentro do intervalo válido
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return new ArrayUnorderedList<T>().iterator(); // Retorna um iterador vazio se os índices não são válidos.
        }

        PriorityQueue<PriorityQueueNode<T>> pq = new PriorityQueue<>();
        double[] distances = new double[numVertices]; // Distâncias do startVertex aos outros.
        int[] predecessors = new int[numVertices]; // Predecessores no caminho mais curto.
        boolean[] visited = new boolean[numVertices]; // Vértices visitados.

        // Inicializa as distâncias e predecessores.
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.POSITIVE_INFINITY;
            predecessors[i] = -1; // -1 indica nenhum predecessor.
            visited[i] = false;
        }
        distances[startIndex] = 0;

        // Adiciona o vértice inicial à fila de prioridade.
        pq.addElement(startVertex, 0);

        while (!pq.isEmpty()) {
            PriorityQueueNode<T> pqNode = pq.removeNext();
            T currentVertex = pqNode.getElement();
            int currentIndex = getIndex(currentVertex);

            if (!visited[currentIndex]) {
                visited[currentIndex] = true;

                // Atualiza as distâncias para todos os vértices adjacentes.
                for (int adjVertexIndex = 0; adjVertexIndex < numVertices; adjVertexIndex++) {
                    if (adjMatrix[currentIndex][adjVertexIndex] && !visited[adjVertexIndex]) {
                        double edgeWeight = weights[currentIndex][adjVertexIndex];
                        double newDist = distances[currentIndex] + edgeWeight;

                        if (newDist < distances[adjVertexIndex]) {
                            distances[adjVertexIndex] = newDist;
                            predecessors[adjVertexIndex] = currentIndex;
                            pq.addElement(vertices[adjVertexIndex], (int) newDist);
                        }
                    }
                }
            }
        }

        // Reconstrói o caminho mais curto do fim para o início usando os predecessores.
        ArrayUnorderedList<T> path = new ArrayUnorderedList<>();
        int current = targetIndex;
        while (current != -1 && predecessors[current] != -1) {
            path.addToFront(vertices[current]);
            current = predecessors[current];
        }
        if (current != -1) {
            path.addToFront(vertices[startIndex]); // Adiciona o vértice inicial ao caminho, se aplicável.
        }
        return path.iterator();
    }

}
