package geogebra.web.gui;

import geogebra.common.gui.CustomizeToolbarModel;
import geogebra.common.gui.toolbar.ToolBar;
import geogebra.common.gui.toolbar.ToolbarItem;
import geogebra.common.main.App;
import geogebra.html5.gui.util.LayoutUtil;
import geogebra.html5.main.AppW;
import geogebra.web.css.GuiResources;
import geogebra.web.gui.CustomizeToolbarHeaderPanel.CustomizeToolbarListener;
import geogebra.web.gui.app.GGWToolBar;
import geogebra.web.gui.layout.DockPanelW;
import geogebra.web.gui.toolbar.ToolBarW;

import java.util.Vector;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class CustomizeToolbarGUI extends MyHeaderPanel
implements CustomizeToolbarListener {

	private AppW app;
	private CustomizeToolbarHeaderPanel header;
	private FlowPanel usedToolsPanel;
	private FlowPanel allToolsPanel;
	private Vector<Integer> usedTools;
	private Vector<Integer> allTools;
	private ToolTree toolTree;
	private static ToolItem dragging = null;
	private static TreeItem allToolsRoot = new TreeItem();
	
	private class ToolTreeResources implements Tree.Resources {

		public ToolTreeResources() {
        }

		public ImageResource treeClosed() {
		//      return AppResources.INSTANCE.tree_open();
		      return GuiResources.INSTANCE.algebra_tree_closed();
	    }

		public ImageResource treeLeaf() {
            return GuiResources.INSTANCE.algebra_tree_closed();
//	        return AppResources.INSTANCE.tree_close();
        }

		public ImageResource treeOpen() {
            return GuiResources.INSTANCE.algebra_tree_open();
	        //return AppResources.INSTANCE.tree_close();
        }
		
	}
	private class ToolTree extends Tree {
		public ToolTree(Tree.Resources res) {
	        super(res);
        }

		public String getToolbarString() {
			// TODO: implement
			return null;
		}
	}

	private class ToolItem extends FlowPanel {
		
		private Integer mode;
		private TreeItem parent;
		private TreeItem treeItem;
		public ToolItem(Integer mode, TreeItem parent) {
			this.mode = mode;
			this.parent = parent;
			treeItem = null; 
			FlowPanel btn = new FlowPanel();
			addStyleName("customizableToolbarItem");
			btn.addStyleName("toolbar_button");
			Image toolbarImg = new Image(((GGWToolBar)app.getToolbar()).getImageURL(mode));
			toolbarImg.addStyleName("toolbar_icon");
			btn.add(toolbarImg);
			String str = app.getMenu(mode == ToolBar.SEPARATOR ? "Separator": app.getToolName(mode));
			setTitle(str);
			Label text = new Label(str);
			add(LayoutUtil.panelRow(btn, text));
			getElement().setAttribute("mode", mode + " ");
		    getElement().setDraggable(Element.DRAGGABLE_TRUE);
	       
		    initDrag();
		    initDrop();
		}

		private void initDrag() {
	        addDomHandler(new DragStartHandler() {
				
				public void onDragStart(DragStartEvent event) {
					App.debug("!DRAG START!");
					dragging = ToolItem.this;
					event.setData("text", "draggginggg");
 	                event.getDataTransfer().setDragImage(getElement(), 10, 10);
		
				}
			}, DragStartEvent.getType());
        }

	}
	

    private void initDrop()
    {
        
//        addDomHandler(new DropHandler()
//        {
//            @Override
//            public void onDrop(DropEvent event)
//            {
//                event.preventDefault();
//                if (dragging != null)
//                {
//                	App.debug("Drop " + dragging.getTitle());
//                	TreeItem parent = dragging.parent;
//                	
//                	if (parent == null) {
//                		return;
//                	}
//                	
//                	if (parent != allToolsRoot) {
////                		usedTools.remove(usedTools.indexOf(dragging.mode));
////             			allToolsPanel.add(new ToolItem(dragging.mode, allToolsRoot));
////             			dragging.treeItem.remove();
////                	} else {
//                		allTools.remove(allTools.indexOf(dragging.mode));
//                		
//                		TreeItem branch = toolTree.getItem(0);
//                		toolTree.add(new ToolItem(dragging.mode, branch));
//                		
//                 	}
//                 dragging = null;
//                }
//            }
//        }, DropEvent.getType());
    };
    
	public CustomizeToolbarGUI(AppW app) {
		this.app = app;
		addHeader();
		addContent();
	}

	private void addContent() {
		FlowPanel main = new FlowPanel();

		usedToolsPanel = new FlowPanel();
		usedToolsPanel.setStyleName("usedToolsPanel");
		toolTree = new ToolTree(new ToolTreeResources());
	
				
		usedToolsPanel.add(toolTree);

		allToolsPanel = new FlowPanel();
		allToolsPanel.setStyleName("allToolsPanel");

		allToolsPanel.addDomHandler(new DropHandler()
        {
            @Override
            public void onDrop(DropEvent event)
            {
                event.preventDefault();
                if (dragging != null)
                {
                	App.debug("Drop " + dragging.getTitle());
                	TreeItem parent = dragging.parent;
                	
                	if (parent == null) {
                		return;
                	}
                	
               		usedTools.remove(usedTools.indexOf(dragging.mode));
           			allToolsPanel.add(new ToolItem(dragging.mode, allToolsRoot));
           			dragging.treeItem.remove();
           			dragging = null;
           			allToolsPanel.removeStyleName("toolBarDropping");
                }
            }
        }, DropEvent.getType());

		allToolsPanel.addDomHandler(new DragOverHandler()
        {
            @Override
            public void onDragOver(DragOverEvent event)
            {
            	allToolsPanel.addStyleName("toolBarDropping");
            }
        }, DragOverEvent.getType());

		allToolsPanel.addDomHandler(new DragLeaveHandler()
        {
            @Override
            public void onDragLeave(DragLeaveEvent event)
            {
            	allToolsPanel.removeStyleName("toolBarDropping");
            }
        }, DragLeaveEvent.getType());

		
		main.add(usedToolsPanel);
		main.add(allToolsPanel);
		setContentWidget(main);
		update(-1);
	}

	public void update(int id) {
		updateUsedTools(id);
		updateAllTools();
	}

	private void updateUsedTools(int id) {

		String toolbarDefinition = null;
		if (id == -1) {
			toolbarDefinition = ((GuiManagerW)app.getGuiManager()).getToolbarDefinition(); 
		} else  {
			DockPanelW panel = ((GuiManagerW)app.getGuiManager()).getLayout().getDockManager().getPanel(id);
			toolbarDefinition = panel.getDefaultToolbarString();
		}

		buildUsedTools(toolbarDefinition);
		//		usedToolsPanel.clear();
		//		
		//		for (Integer mode: usedTools) {
		//			usedToolsPanel.add(buildItem(mode));
		//		}
		//		
		App.debug("[CUSTOMIZE] " + usedTools);
	}

	private void updateAllTools() {
		allTools = CustomizeToolbarModel
				.generateToolsVector(ToolBarW.getAllTools(app));

		allToolsPanel.clear();

		//		allToolsPanel.add(buildItem(ToolBar.SEPARATOR));

		for (Integer mode: allTools) {
			if (!usedTools.contains(mode)) {
				ToolItem tool = new ToolItem(mode, allToolsRoot);
				allToolsPanel.add(tool);
			}
		}
	}

	private void addHeader() {
		header = new CustomizeToolbarHeaderPanel(app, this);
		setHeaderWidget(header);

	}

	public void setLabels() {
		if (header != null) {
			header.setLabels();
		}
	}

	public void buildUsedTools(String toolbarDefinition) {
		toolTree.clear();
		if (usedTools == null) {
			usedTools = new Vector<Integer>();
		}
		usedTools.clear();
		// separator
		//usedTools.add(ToolBar.SEPARATOR);
		
		// get default toolbar as nested vectors
		Vector<ToolbarItem> defTools = null;
		
		defTools = ToolBar.parseToolbarString(toolbarDefinition);
		for (int i = 0; i < defTools.size(); i++) {
			ToolbarItem element = defTools.get(i);
			Integer m = element.getMode();
		
			if (element.getMenu() != null) {
				Vector<Integer> menu = element.getMenu();
				final ToolItem tool = new ToolItem(menu.get(0), null);
				final TreeItem current = toolTree.addItem(tool);
				tool.addDomHandler(new DropHandler()
		        {
		            @Override
		            public void onDrop(DropEvent event)
		            {
		            	App.debug("Drop on branch item!");
		                event.preventDefault();
		                if (dragging != null)
		                {
		                	App.debug("Drop " + dragging.getTitle());
		                	TreeItem parent = dragging.parent;
		                	
		                	allTools.remove(allTools.indexOf(dragging.mode));
	                		allToolsPanel.remove(dragging);
	                		current.addItem(new ToolItem(dragging.mode, current));
	                		dragging = null;
       		            	tool.removeStyleName("toolBarDropping");
		                }
		            }
		        }, DropEvent.getType());
		
				tool.addDomHandler(new DragOverHandler()
		        {
		            @Override
		            public void onDragOver(DragOverEvent event)
		            {
		            	tool.addStyleName("toolBarDropping");
		            }
		        }, DragOverEvent.getType());

				tool.addDomHandler(new DragLeaveHandler()
		        {
		            @Override
		            public void onDragLeave(DragLeaveEvent event)
		            {
		            	tool.removeStyleName("toolBarDropping");
		            }
		        }, DragLeaveEvent.getType());
				
				for (int j = 0; j < menu.size(); j++) {
					Integer modeInt = menu.get(j);
					int mode = modeInt.intValue();
					if (mode != -1)
						usedTools.add(modeInt);
						ToolItem leaf = new ToolItem(modeInt, current);
						TreeItem t = current.addItem(leaf);
						leaf.treeItem = t;
					}
			}
//			else {
//				Integer modeInt = element.getMode();
//				int mode = modeInt.intValue();
//				if (mode != -1)
//					usedTools.add(modeInt);
//	     			//current = current.addItem(buildItem(modeInt));
//			}
		}
	}

}

