package frc.team254.lib.trajectory;

/*
 * import org.jfree.chart.ChartFactory; import org.jfree.chart.ChartPanel; import
 * org.jfree.chart.JFreeChart; import org.jfree.chart.axis.NumberAxis; import
 * org.jfree.chart.axis.NumberTickUnit; import org.jfree.chart.plot.PlotOrientation; import
 * org.jfree.chart.plot.XYPlot; import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer; import
 * org.jfree.data.xy.XYDataset; import org.jfree.data.xy.XYSeries; import
 * org.jfree.data.xy.XYSeriesCollection; import org.jfree.ui.ApplicationFrame;
 * 
 * import java.awt.*;
 */


/* @SuppressWarnings("serial") */ public class PathVisualizer /* extends ApplicationFrame */ {


  /*
   * public PathVisualizer(String applicationTitle, String chartTitle, Trajectory traj1, Trajectory
   * traj2) { super(applicationTitle); JFreeChart xylineChart = ChartFactory
   * .createXYLineChart(chartTitle, "Category", "Score", createDataset(traj1, traj2),
   * PlotOrientation.VERTICAL, true, true, false);
   * 
   * ChartPanel chartPanel = new ChartPanel(xylineChart); chartPanel.setPreferredSize(new
   * java.awt.Dimension(500, 1000)); final XYPlot plot = xylineChart.getXYPlot();
   * 
   * XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); renderer.setSeriesPaint(0,
   * Color.RED); renderer.setSeriesPaint(1, Color.GREEN); renderer.setSeriesPaint(2, Color.YELLOW);
   * renderer.setSeriesStroke(0, new BasicStroke(4.0f)); renderer.setSeriesStroke(1, new
   * BasicStroke(3.0f)); renderer.setSeriesStroke(2, new BasicStroke(2.0f));
   * plot.setRenderer(renderer); setContentPane(chartPanel);
   * 
   * 
   * NumberAxis domain = (NumberAxis) plot.getDomainAxis(); domain.setRange(-50, 50);
   * domain.setTickUnit(new NumberTickUnit(5)); domain.setVerticalTickLabels(true); NumberAxis range
   * = (NumberAxis) plot.getRangeAxis(); range.setRange(0.0, 200.0); range.setTickUnit(new
   * NumberTickUnit(10)); range.setVerticalTickLabels(true); }
   * 
   * 
   * public PathVisualizer(String applicationTitle, String chartTitle, Trajectory traj1, Trajectory
   * traj2, boolean yes) { super(applicationTitle);
   * 
   * JFreeChart xylineChart = ChartFactory .createXYLineChart(chartTitle, "Category", "Score",
   * createDataset1(traj1, traj2), PlotOrientation.VERTICAL, true, true, false);
   * 
   * ChartPanel chartPanel = new ChartPanel(xylineChart); chartPanel.setPreferredSize(new
   * java.awt.Dimension(500, 1000)); final XYPlot plot = xylineChart.getXYPlot();
   * 
   * XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); renderer.setSeriesPaint(0,
   * Color.RED); renderer.setSeriesPaint(1, Color.GREEN); renderer.setSeriesPaint(2, Color.YELLOW);
   * renderer.setSeriesStroke(0, new BasicStroke(4.0f)); renderer.setSeriesStroke(1, new
   * BasicStroke(3.0f)); renderer.setSeriesStroke(2, new BasicStroke(2.0f));
   * plot.setRenderer(renderer); setContentPane(chartPanel);
   * 
   * 
   * NumberAxis domain = (NumberAxis) plot.getDomainAxis(); domain.setRange(0, 10);
   * domain.setTickUnit(new NumberTickUnit(5)); domain.setVerticalTickLabels(true); NumberAxis range
   * = (NumberAxis) plot.getRangeAxis(); range.setRange(0.0, 100.0); range.setTickUnit(new
   * NumberTickUnit(10)); range.setVerticalTickLabels(true); }
   * 
   * private XYDataset createDataset(Trajectory traj1, Trajectory traj2) {
   * 
   * final XYSeries left = new XYSeries("Left"); for (int i = 0; i < traj1.getNumSegments(); ++i) {
   * Segment segment = traj1.getSegment(i); left.add(segment.y, segment.x); }
   * 
   * final XYSeries right = new XYSeries("Right"); for (int i = 0; i < traj2.getNumSegments(); ++i)
   * { Segment segment = traj2.getSegment(i); right.add(segment.y, segment.x); } final
   * XYSeriesCollection dataset = new XYSeriesCollection(); dataset.addSeries(left);
   * dataset.addSeries(right); return dataset; }
   * 
   * private XYDataset createDataset1(Trajectory traj1, Trajectory traj2) {
   * 
   * final XYSeries vel = new XYSeries("Velocity"); for (int i = 0; i < traj1.getNumSegments(); ++i)
   * { Segment segment = traj1.getSegment(i); vel.add(segment.dt * i, segment.vel); }
   * 
   * final XYSeries acc = new XYSeries("Acceleration"); for (int i = 0; i < traj1.getNumSegments();
   * ++i) { Segment segment = traj1.getSegment(i); acc.add(segment.dt * i, segment.acc); }
   * 
   * final XYSeriesCollection dataset = new XYSeriesCollection(); dataset.addSeries(vel);
   * dataset.addSeries(acc); return dataset; }
   */
}
