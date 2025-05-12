
<script>
$(document).ready(function() {
    // Suppress DataTables warning alerts
    $.fn.dataTable.ext.errMode = 'none';

    // Check if the table is already initialized
    if ($.fn.DataTable.isDataTable('.datanew')) {
        // Destroy the existing instance
        $('.datanew').DataTable().clear().destroy();
    }

    // Initialize DataTable
    $('.datanew').DataTable({
        "paging": false,  // Disable pagination
        "searching": true,  // Enable searching (default is true)
        "ordering": true,  // Enable ordering (default is true)
        "info": false,  // Hide information display
        "responsive": true  // Enable responsive mode
        // Additional options and configurations can be added here
    });
});
</script>

