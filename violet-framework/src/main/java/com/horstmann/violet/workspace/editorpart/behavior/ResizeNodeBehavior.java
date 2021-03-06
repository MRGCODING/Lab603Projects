package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.workspace.editorpart.EditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;


public class ResizeNodeBehavior extends AbstractEditorPartBehavior
{
    public ResizeNodeBehavior(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
    {
        this.editorPart = editorPart;
        this.graph = editorPart.getGraph();
        this.selectionHandler = editorPart.getSelectionHandler();
        this.graphToolsBar = graphToolsBar;
    }
    @Override
    public void onMouseMoved(MouseEvent event)
    {
    	 List<INode> selectedNodes = selectionHandler.getSelectedNodes();
         //获取到选择到的点
         List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();
          //获取到选择到的边
         boolean isOnlyOneNodeSelected = (selectedNodes.size() == 1 && selectedEdges.size() == 0);
          //只有一个节点被选取
         if (!isOnlyOneNodeSelected) {
             return;//跳出函数
         }
         INode selectedNode = selectedNodes.get(0);//获取到被选取的这一个单独的节点
         if (!selectedNode.getClass().isAssignableFrom(IResizableNode.class)) {
             return;
         }
         double zoom = editorPart.getZoomFactor();//获取缩放控件因子 
         final Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
         Rectangle2D bounds = new Rectangle2D.Double(selectedNode.getBounds().getX(),selectedNode.getBounds().getY(),5,5);
         if (bounds.contains(mousePoint)) {
             editorPart.getSwingComponent().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
         } else {
             editorPart.getSwingComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
         }
    }   
    private IGraph graph;
    private Point2D lastMousePoint = null;
    private IEditorPartSelectionHandler selectionHandler;
    private IEditorPart editorPart;
    private IGraphToolsBar graphToolsBar;
    private boolean isReadyForDragging = false;
}
