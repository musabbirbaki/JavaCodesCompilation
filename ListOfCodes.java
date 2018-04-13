/*
This method returns a JsonObject using URL as String
*/
public static JsonObject readJsonFromUrl(String url) throws  IOException, JsonParseException{
    InputStream is = new URL(url).openStream();
    try {
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = readAll(rd);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonText).getAsJsonObject();
        return json;
    }finally {
        is.close();
    }
}
public static String readAll(Reader rd)throws IOException{
    StringBuilder sb = new StringBuilder();
    int cp;
    while((cp = rd.read()) != -1){
        sb.append((char) cp);
    }
    return sb.toString();
}
//Example MAIN
public void main(String[] args) throws IOException, JsonParseException{
    String apiKey = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&apikey=L7MRW837I3INEZ24";
    JsonObject json = readJsonFromUrl(apiKey);
    JsonObject values = json.get("Time Series (Daily)").getAsJsonObject();

    for (Map.Entry<String, JsonElement> entry : values.entrySet()){
        String date = entry.getKey();
        float closePrice = entry.getValue().getAsJsonObject().get("4. close").getAsFloat();
        System.out.printf("%s : $%.2fe \n", date, closePrice);
    }
}
//////////////////////////////END OF JSON////////////////////////////////





/*
LOOK AT LAB 06 for bar chart and pie chart.
Pie Chart
*/
{
    public void drawPieChart(int ox, int oy, int w, int h, Set<String> labels, Color[] colors) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.strokeArc(ox, oy, w, h, 0, 360, ArcType.OPEN);

        Collection<Integer> valuesTemp = warningType.values();
        Integer[] values = new Integer[valuesTemp.size()];
        valuesTemp.toArray(values);

        //sum
        double totalSum = 0;
        for(int i = 0; i < values.length; i++){
            totalSum += values[i];
        }

        double jump = 0;
        for(int i = 0; i < values.length; i++){
            g.setFill(colors[i]);
            //draw the elements in the series
            g.fillArc(ox, oy, w, h, jump, (values[i]/totalSum) * 360, ArcType.ROUND);
            jump += (values[i]/totalSum) * 360;
        }
    }
    public void drawLegend(int ox, int oy, int w, int h, Set<String> labels, Color[] colors, Group root){
        ArrayList<Label> textlabels = new ArrayList<>();
        StackPane legend = new StackPane();
        GraphicsContext g = canvas.getGraphicsContext2D();
        int translate = 0;
        for(String label: labels){
            Label lb = new Label(label);
            lb.setTranslateY(translate + oy);
            lb.setTranslateX(13 + ox);
            legend.getChildren().add(lb);
            translate+=12;
        }
        int y = 0;
        int warnings = warningType.size();
        for(Color color: colors){
            if(warnings > 0) {
                g.setFill(color);
                g.fillRect(0 + ox, y + oy, 12, 12);
                y += 12;
                warnings--;
            }
        }

        root.getChildren().add(legend);
    }

    //Example Data and runner
    private static Map<String, Integer> warningType = new TreeMap<>();
    @Override
        public void start(Stage primaryStage) throws Exception{
            Group root = new Group();
            Scene scene = new Scene(root, 500,425, Color.WHITE);
            this.canvas = new Canvas();
            this.canvas.widthProperty().bind(primaryStage.widthProperty());
            this.canvas.heightProperty().bind(primaryStage.heightProperty());
            root.getChildren().add(canvas);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lab07 Warning Pie");
            primaryStage.show();

            Color[] pieColours = {
                    Color.AQUA, Color.GOLD, Color.DARKORANGE,
                    Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM, Color.ALICEBLUE
            };

            getData("src/weatherwarnings-2015.csv", ",");
            drawPieChart(130, 50, 325, 325, warningType.keySet(), pieColours);
            drawLegend(10, 50, 325, 325, warningType.keySet(), pieColours, root);
        }
}
/////////////////////////////////END OF PIE CHART///////////////////

//LAB 06 copy bar and pie chart
{
    ///
    import javafx.application.Application;
    import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.ArcType;
    import javafx.stage.Stage;

    public class Main extends Application {

        /**
         * Bar chart data
         * */

        private static double[] avgHousingPricesByYear = {
                247381.0,264171.4,287715.3,294736.1,
                308431.4,322635.9,340253.0,363153.7
        };
        private static double[] avgCommercialPricesByYear = {
                1121585.3,1219479.5,1246354.2,1295364.8,
                1335932.6,1472362.0,1583521.9,1613246.3
        };


        //Pie Char stuff
        private static String[] ageGroups = {
                "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
        };
        private static int[] purchasesByAgeGroup = {
                648, 1021, 2453, 3173, 1868, 2247
        };
        private static Color[] pieColours = {
                Color.AQUA, Color.GOLD, Color.DARKORANGE,
                Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
        };

        private Canvas canvas;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            Group root = new Group();
            Scene scene = new Scene(root, 800,425, Color.WHITE);

            this.canvas = new Canvas();
            this.canvas.widthProperty().bind(primaryStage.widthProperty());
            this.canvas.heightProperty().bind(primaryStage.heightProperty());

            root.getChildren().add(canvas);

            primaryStage.setTitle("Lab 06 In class bar graph");
            primaryStage.setScene(scene);
            primaryStage.show();

            //draw the Graphics
            drawBarChart(50, 50, 325, 325, new Series(avgHousingPricesByYear, Color.RED),
                    new Series(avgCommercialPricesByYear, Color.BLUE));

    //        drawPieChart(430, 50, 325, 325, new Series(avgHousingPricesByYear, Color.RED),
    //                        new Series(avgCommercialPricesByYear, Color.BLUE));

            //drawPieChart(430, 50, 325, 325, labels, values, colors);
            drawPieChart(430, 50, 325, 325, ageGroups, purchasesByAgeGroup, pieColours);
        }

        private void drawBarChart(int ox, int oy, int w, int h, Series... series){
            GraphicsContext g = canvas.getGraphicsContext2D();

            //get the max and min values of all the series
            double max = Double.NEGATIVE_INFINITY, min = 0;

            int maxLen = 0;

            for(Series s: series){
                if(s.values.length > maxLen){
                    maxLen = s.values.length;
                }

                for(double x: s.values){
                    if(x > max){
                        max = x;
                    }
                    if(x < min){
                        min = x;
                    }
                }
            }

            //start drawing each series
            double spacing = 10;
            double jump = (w - 2*spacing)/(series.length*(maxLen + 2));

            for(int i = 0; i < series.length; i++){
                g.setFill(series[i].colors);
                double[] arr = series[i].values;
                double x = ox + jump * i + spacing/2;

                //draw the elements in the series
                for(int j = 0; j < arr.length; j++){
                    double height = ((arr[j] - min)/ (max - min)) * h;
                    g.strokeRect(x, oy+h-height, jump, height);
                    g.fillRect(x, oy+h-height, jump, height);
                    x += jump*series.length + spacing;
                }
            }

            g.strokeRect(ox, oy, w, h);
        }

        public void drawPieChart(int ox, int oy, int w, int h, String[] labels, int[] values, Color[] colors) {
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.strokeArc(ox, oy, w, h, 0, 360, ArcType.OPEN);

            //sum
            double totalSum = 0;
            for(int i = 0; i < values.length; i++){
                totalSum += values[i];
            }

            double jump = 0;
            for(int i = 0; i < values.length; i++){
                g.setFill(colors[i]);
                //draw the elements in the series
                g.fillArc(ox, oy, w, h, jump, (values[i]/totalSum) * 360, ArcType.ROUND);
                jump += (values[i]/totalSum) * 360;
            }
        }


        public static class Series{
            public double[] values;
            public Color colors;
            public String label;

            public Series(){
                this.values = new double[0];
                this.colors = Color.WHITE;
            }

            public Series(double[] values, Color color){
                this.values = values;
                this.colors = color;
            }

            public Series(double[] values, Color color, String label){
                this.values = values;
                this.colors = color;
                this.label = label;
            }

            public double[] getValues() {
                return values;
            }

            public void setValues(double[] values) {
                this.values = values;
            }

            public Color getColors() {
                return colors;
            }

            public void setColors(Color colors) {
                this.colors = colors;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

    }
///////
}
///////////////////////////////////END OF LAB 6

/*
File write and get data
*/

/**
 * Gets data from path and using delimiter it splits, can be " ", or ","
 */
{
    public void getData(String path, String delimiter) {
        this.data.clear();
        List<String> srcList = new ArrayList<>();
        // Special Case
        if (path == null || path.length() == 0) {
            return;
        }
        if(delimiter == null || delimiter.length() == 0) {
            delimiter = ",";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            //Read the next line until end of file
            for (String line; (line = br.readLine()) != null;) {
                //Parse the line
                String[] values = line.split(delimiter);
    //                for(int i = 0; i < values.length; i++) {
    //                    System.out.print(values[i] + " | ");
    //                }
    //                System.out.print(values[0] + " " + values[1] + " " + values[2] + " " + values[3]);
                this.data.add(new StudentRecord(values[0],
                        Float.parseFloat(values[1]),
                        Float.parseFloat(values[2]),
                        Float.parseFloat(values[3])));
    //                System.out.println("\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method writes to filePath, which is the abs file path, and writes the content
    private void writeToFile(String filePath, String content) throws IOException {
        FileWriter fileOut = new FileWriter(filePath);
        fileOut.append(content);
        fileOut.close();
    }
    ////
}

////////////////////////////////END OF FILE READ AND WRITE

//directory chooser
{
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File("."));
    File mainDirectory = directoryChooser.showDialog(fileChooserStage);
    String fileDirectoryPath = mainDirectory.getPath();
}

//Example open function
@FXML public void openFunction(){
    System.out.println("Open Function Run.");
    clearTextFields();
    Stage fileChooser = new Stage();
    fileChooser.initModality(Modality.APPLICATION_MODAL);
    //fileChooser.show();
    FileChooser openChooser = new FileChooser();
    openChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
    File selectedFile = openChooser.showOpenDialog(fileChooser);
    this.currentFilePath = selectedFile.getPath();

    System.out.println("Open Path: ");
    System.out.println(this.currentFilePath);
    getData(this.currentFilePath, ",");
}








///////////////////////////Some querying

//finding a row
public Employee findEmployeeByName(String name) {
    TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findByName", Employee.class);

//@Entity
//@NamedQuery(name="Employee.findByName",query="SELECT e FROM Employee e WHERE e.name = :name")
//public class Employee {

    query.setParameter("name", name);
    Employee employee = query.getSingleResult();
    return employee;
  }

//searching using keyword LIKE
public void findEmployees(String keyword) {
      //get table
    TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.name like concat('%', :keyword, '%')",
                                                           Employee.class);

    query.setParameter("keyword", keyword);
    List<Employee> results = query.getResultList();
    for (Employee employee: results) {
      System.out.println(employee.getName());
    }
  }

//delete
  public void deleteAllProjects() {
    entityManager.getTransaction().begin();
    entityManager.createQuery("delete from Project p").executeUpdate();
    entityManager.getTransaction().commit();
  }

/////////////////////////////////Internal PSQL lines
@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long prodID;
	@Column(name="desc")
	private String name;
	…
}

///delete
Product newProduct = new Product();
newProduct.setName("Falafel");
newProduct.setCategory("Sandwich");
newProduct.setPrice(3.99);
entityManager.persist(newProduct);
…
entityManager.remove(newProduct);

///Update
Product newProduct = new Product();
newProduct.setName("Falafel");
newProduct.setCategory("Sandwich");
newProduct.setPrice(3.99);
entityManager.persist(newProduct);
…
newProduct.setPrice(3.49);

///Create
Product newProduct = new Product();
newProduct.setName("Falafel");
newProduct.setCategory("Sandwich");
newProduct.setPrice(3.99);
entityManager.persist(newProduct);

///Read
//entityManager.find(EntityClass.class, primaryKey);
long productId = 1;
Product product = entityManager.find(Product.class,
productId);

//Read2
String queryStr = "select p from Product p";
Query query = entityManager.createQuery(queryStr);
List<Product> products = query.getResultList();
for (Product product: products) {
	System.out.println(product.getName());
}


//Read3
@Entity
@NamedQuery(name="Product.findByName", query="SELECT p FROM Product p WHERE p.name = :name")
public class Product {
	…	
}
…
TypedQuery<Product> query = entityManager.createNamedQuery("Product.findByName", Product.class);
query.setParameter("name", name);
Product product = query.getSingleResult();

//RelationShip
Entity relationships: – @OneToOne
– @OneToMany
– @ManyToOne
– @ManyToMany

public class SportsTeam {
@OneToOne
private Coach coach;
}
public class Coach {
@OneToOne(mappedBy="coach") // sports team is the ‘owner’
private SportsTeam team;
}
