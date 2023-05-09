public interface IGraphMaker {
    /**
     *
     * @param filepath to read weight file from
     * @return number of vertices read into the graph
     */
    int readWeights(String filepath);


    /**
     * @param filepath to read coordinates from
     * @return number of vertices read into the graph
     */
    int readCords(String filepath);
}
