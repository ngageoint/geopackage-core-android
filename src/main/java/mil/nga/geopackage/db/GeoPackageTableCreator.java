package mil.nga.geopackage.db;

import java.sql.SQLException;
import java.util.List;

import mil.nga.geopackage.GeoPackageException;
import mil.nga.geopackage.core.srs.SpatialReferenceSystem;
import mil.nga.geopackage.core.srs.SpatialReferenceSystemDao;
import mil.nga.geopackage.io.ResourceIOUtils;
import mil.nga.geopackage.property.GeoPackageProperties;
import mil.nga.geopackage.property.PropertyConstants;
import mil.nga.geopackage.user.UserColumn;
import mil.nga.geopackage.user.UserTable;
import mil.nga.geopackage.user.UserUniqueConstraint;

import com.j256.ormlite.dao.DaoManager;

/**
 * Executes database scripts to create GeoPackage tables
 * 
 * @author osbornb
 */
public class GeoPackageTableCreator {

	/**
	 * SQLite database
	 */
	private final GeoPackageCoreConnection db;

	/**
	 * Constructor
	 * 
	 * @param db
	 *            db connection
	 */
	public GeoPackageTableCreator(GeoPackageCoreConnection db) {
		this.db = db;
	}

	/**
	 * Create Spatial Reference System table and views
	 * 
	 * @return executed statements
	 */
	public int createSpatialReferenceSystem() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "spatial_reference_system"));
	}

	/**
	 * Create Contents table
	 * 
	 * @return executed statements
	 */
	public int createContents() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "contents"));
	}

	/**
	 * Create Geometry Columns table
	 * 
	 * @return executed statements
	 */
	public int createGeometryColumns() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "geometry_columns"));
	}

	/**
	 * Create Tile Matrix Set table
	 * 
	 * @return executed statements
	 */
	public int createTileMatrixSet() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "tile_matrix_set"));
	}

	/**
	 * Create Tile Matrix table
	 * 
	 * @return executed statements
	 */
	public int createTileMatrix() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "tile_matrix"));
	}

	/**
	 * Create Data Columns table
	 * 
	 * @return executed statements
	 */
	public int createDataColumns() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "data_columns"));
	}

	/**
	 * Create Data Column Constraints table
	 * 
	 * @return executed statements
	 */
	public int createDataColumnConstraints() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "data_column_constraints"));
	}

	/**
	 * Create Metadata table
	 * 
	 * @return executed statements
	 */
	public int createMetadata() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "metadata"));
	}

	/**
	 * Create Metadata Reference table
	 * 
	 * @return executed statements
	 */
	public int createMetadataReference() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "metadata_reference"));
	}

	/**
	 * Create Extensions table
	 * 
	 * @return executed statements
	 */
	public int createExtensions() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "extensions"));
	}

	/**
	 * Create the Tiled Gridded Coverage Data Coverage extension table
	 * 
	 * @return executed statements
	 * @since 1.2.1
	 */
	public int createGriddedCoverage() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "2d_gridded_coverage"));
	}

	/**
	 * Create the Tiled Gridded Coverage Data Tile extension table
	 * 
	 * @return executed statements
	 * @since 1.2.1
	 */
	public int createGriddedTile() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "2d_gridded_tile"));
	}

	/**
	 * Create the Extended Relations table
	 * 
	 * @return executed statements
	 * @since 3.0.1
	 */
	public int createExtendedRelations() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "extended_relations"));
	}

	/**
	 * Create Table Index table
	 * 
	 * @return executed statements
	 * @since 1.1.0
	 */
	public int createTableIndex() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "table_index"));
	}

	/**
	 * Create Geometry Index table
	 * 
	 * @return executed statements
	 * @since 1.1.0
	 */
	public int createGeometryIndex() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "geometry_index"))
				+ indexGeometryIndex();
	}

	/**
	 * Create Geometry Index table column indexes
	 * 
	 * @return executed statements
	 * @since 3.1.0
	 */
	public int indexGeometryIndex() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL + PropertyConstants.PROPERTY_DIVIDER
						+ "geometry_index", "index"));
	}

	/**
	 * Un-index (drop) Geometry Index table column indexes
	 * 
	 * @return executed statements
	 * @since 3.1.0
	 */
	public int unindexGeometryIndex() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL + PropertyConstants.PROPERTY_DIVIDER
						+ "geometry_index", "unindex"));
	}

	/**
	 * Create Feature Tile Link table
	 * 
	 * @return executed statements
	 * @since 1.1.5
	 */
	public int createFeatureTileLink() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "feature_tile_link"));
	}

	/**
	 * Create Tile Scaling table
	 * 
	 * @return executed statements
	 * @since 2.0.2
	 */
	public int createTileScaling() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "tile_scaling"));
	}

	/**
	 * Create Contents Id table
	 * 
	 * @return executed statements
	 * @since 3.2.0
	 */
	public int createContentsId() {
		return execSQLScript(GeoPackageProperties.getProperty(
				PropertyConstants.SQL, "contents_id"));
	}

	/**
	 * Execute the SQL Script
	 * 
	 * @param sqlScript
	 *            SQL script property file name
	 * @return executed statements
	 */
	private int execSQLScript(String sqlScript) {

		String path = GeoPackageProperties.getProperty(PropertyConstants.SQL,
				"directory");
		List<String> statements = ResourceIOUtils.parseSQLStatements(path,
				sqlScript);

		for (String statement : statements) {
			db.execSQL(statement);
		}

		return statements.size();
	}

	/**
	 * Create the user defined table
	 * 
	 * @param table
	 *            user table
	 * @param <TColumn>
	 *            column type
	 */
	public <TColumn extends UserColumn> void createTable(
			UserTable<TColumn> table) {

		// Verify the table does not already exist
		if (db.tableExists(table.getTableName())) {
			throw new GeoPackageException(
					"Table already exists and can not be created: "
							+ table.getTableName());
		}

		// Build the create table sql
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ")
				.append(CoreSQLUtils.quoteWrap(table.getTableName()))
				.append(" (");

		// Add each column to the sql
		List<? extends UserColumn> columns = table.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			UserColumn column = columns.get(i);
			if (i > 0) {
				sql.append(",");
			}
			sql.append("\n  ").append(CoreSQLUtils.quoteWrap(column.getName()))
					.append(" ").append(column.getTypeName());
			if (column.getMax() != null) {
				sql.append("(").append(column.getMax()).append(")");
			}
			if (column.isNotNull()) {
				sql.append(" NOT NULL");
			}
			if (column.isPrimaryKey()) {
				sql.append(" PRIMARY KEY AUTOINCREMENT");
			}
		}

		// Add unique constraints
		List<UserUniqueConstraint<TColumn>> uniqueConstraints = table
				.getUniqueConstraints();
		for (int i = 0; i < uniqueConstraints.size(); i++) {
			UserUniqueConstraint<TColumn> uniqueConstraint = uniqueConstraints
					.get(i);
			sql.append(",\n  UNIQUE (");
			List<TColumn> uniqueColumns = uniqueConstraint.getColumns();
			for (int j = 0; j < uniqueColumns.size(); j++) {
				TColumn uniqueColumn = uniqueColumns.get(j);
				if (j > 0) {
					sql.append(", ");
				}
				sql.append(uniqueColumn.getName());
			}
			sql.append(")");
		}

		sql.append("\n);");

		// Create the table
		db.execSQL(sql.toString());
	}

	/**
	 * Create the minimum required GeoPackage tables
	 */
	public void createRequired() {

		// Create the Spatial Reference System table (spec Requirement 10)
		createSpatialReferenceSystem();

		// Create the Contents table (spec Requirement 13)
		createContents();

		// Create the required Spatial Reference Systems (spec Requirement
		// 11)
		try {
			SpatialReferenceSystemDao dao = DaoManager.createDao(
					db.getConnectionSource(), SpatialReferenceSystem.class);
			dao.createWgs84();
			dao.createUndefinedCartesian();
			dao.createUndefinedGeographic();
		} catch (SQLException e) {
			throw new GeoPackageException(
					"Error creating default required Spatial Reference Systems",
					e);
		}
	}

	/**
	 * Drop the table if it exists
	 * 
	 * @param table
	 *            table name
	 * @since 1.1.5
	 */
	public void dropTable(String table) {
		db.execSQL("DROP TABLE IF EXISTS " + CoreSQLUtils.quoteWrap(table));
	}

}
